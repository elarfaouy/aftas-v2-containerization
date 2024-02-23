package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.Role;
import com.youcode.aftas.domain.entity.Token;
import com.youcode.aftas.domain.entity.User;
import com.youcode.aftas.dto.payload.AuthenticationDto;
import com.youcode.aftas.dto.store.UserLoginDto;
import com.youcode.aftas.dto.store.UserRegisterDto;
import com.youcode.aftas.repository.UserRepository;
import com.youcode.aftas.service.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthenticationDto register(UserRegisterDto registerDto) {
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User user = modelMapper.map(registerDto, User.class);
        user.setRole(Role.builder().id(3L).build());

        User saved = userRepository.save(user);

        Token refreshToken = tokenService.generateRefreshToken(saved);

        String token = tokenService.generateToken(saved, refreshToken);

        return AuthenticationDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .tokenExpiration(tokenService.extractExpiration(token))
                .build();
    }

    @Override
    public AuthenticationDto login(UserLoginDto loginDto) {
        User userFromDb = userRepository
                .findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        Token refreshToken = tokenService.generateRefreshToken(userFromDb);

        String token = tokenService.generateToken(userFromDb, refreshToken);

        return AuthenticationDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .tokenExpiration(tokenService.extractExpiration(token))
                .build();
    }

    @Override
    public void logout(User user) {
        tokenService.revokeRefreshTokensByUser(user);
        SecurityContextHolder.clearContext();
    }
}
