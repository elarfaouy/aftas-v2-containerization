package com.youcode.aftas.web.rest;

import com.youcode.aftas.domain.entity.Permission;
import com.youcode.aftas.domain.entity.User;
import com.youcode.aftas.dto.payload.AuthenticationDto;
import com.youcode.aftas.dto.payload.RefreshTokenRequest;
import com.youcode.aftas.dto.payload.UserDto;
import com.youcode.aftas.dto.store.UserLoginDto;
import com.youcode.aftas.dto.store.UserRegisterDto;
import com.youcode.aftas.service.IAuthenticationService;
import com.youcode.aftas.service.ITokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationRest {
    private final IAuthenticationService authenticationService;
    private final ITokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDto> register(@Valid @RequestBody UserRegisterDto user) {
        return ResponseEntity.ok(
                authenticationService.register(user)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@Valid @RequestBody UserLoginDto user) {
        return ResponseEntity.ok(
                authenticationService.login(user)
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationDto> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(
                tokenService.generateNewAccessToken(refreshTokenRequest.getRefreshToken())
        );
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUser() throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {
            return ResponseEntity.ok(
                    UserDto.builder()
                            .num(user.getNum())
                            .name(user.getName())
                            .familyName(user.getFamilyName())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .role(user.getRole().getName())
                            .permissions(user.getRole().getPermissions().stream().map(Permission::getName).toList())
                            .build()
            );
        }

        return ResponseEntity.ok(
                UserDto.builder()
                        .username("anonymous")
                        .role("anonymous")
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User user) {
            authenticationService.logout(user);

            return ResponseEntity.ok(
                    Map.of("message", "You have been logged out")
            );
        }

        return ResponseEntity.badRequest().body(
                Map.of("message", "You are not logged in")
        );
    }
}
