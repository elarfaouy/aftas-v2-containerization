package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.Token;
import com.youcode.aftas.domain.entity.User;
import com.youcode.aftas.dto.payload.AuthenticationDto;
import com.youcode.aftas.exception.DataBaseConstraintException;
import com.youcode.aftas.repository.TokenRepository;
import com.youcode.aftas.service.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TokenService implements ITokenService {
    private final TokenRepository tokenRepository;

    @Value("${application.security.secret-key}")
    private String secretKey;
    @Value("${application.security.token-expiration}")
    private Long tokenExpirationTime;
    @Value("${application.security.refresh-token-expiration}")
    private Long refreshTokenExpirationTime;

    @Override
    public String generateToken(UserDetails userDetails, Token refreshToken) {
        return generateToken(userDetails, Map.of("uuid", refreshToken.getUuid()));
    }

    private String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && !isRefreshTokenRevoked(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private UUID extractUuid(String token) {
        return extractClaim(token, claims -> UUID.fromString((String) claims.get("uuid")));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] decodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private Boolean isRefreshTokenRevoked(String token) {
        UUID uuid = extractUuid(token);
        Token refreshToken = tokenRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Invalid uuid for refresh token"));
        return refreshToken.getRevoked();
    }

    @Override
    public Token generateRefreshToken(User user) {
        // before generating a new refresh token, we need to revoke all existing refresh tokens for the user
        revokeRefreshTokensByUser(user);

        Token token = new Token();
        token.setRevoked(false);
        token.setToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()));
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenExpirationTime));
        token.setUuid(UUID.randomUUID());
        token.setUser(user);

        return tokenRepository.save(token);
    }

    @Override
    public void revokeRefreshTokensByUser(User user) {
        List<Token> tokens = tokenRepository.findByUser(user);
        tokens.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(tokens);
    }

    @Override
    public AuthenticationDto generateNewAccessToken(String refreshToken) {
        Token token = tokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new DataBaseConstraintException("Invalid refresh token"));

        if (token.getRevoked()) {
            throw new DataBaseConstraintException("Refresh token has been revoked");
        }

        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new DataBaseConstraintException("Refresh token has expired");
        }

        User user = token.getUser();
        String accessToken = generateToken(user, token);

        return AuthenticationDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpiration(extractExpiration(accessToken))
                .build();
    }
}
