package com.youcode.aftas.service;

import com.youcode.aftas.domain.entity.Token;
import com.youcode.aftas.domain.entity.User;
import com.youcode.aftas.dto.payload.AuthenticationDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface ITokenService {
    String generateToken(UserDetails userDetails, Token refreshToken);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUsername(String token);

    Date extractExpiration(String token);

    Token generateRefreshToken(User user);

    void revokeRefreshTokensByUser(User user);

    AuthenticationDto generateNewAccessToken(String refreshToken);
}
