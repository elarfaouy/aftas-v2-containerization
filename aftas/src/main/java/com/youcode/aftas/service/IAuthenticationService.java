package com.youcode.aftas.service;

import com.youcode.aftas.domain.entity.User;
import com.youcode.aftas.dto.payload.AuthenticationDto;
import com.youcode.aftas.dto.store.UserLoginDto;
import com.youcode.aftas.dto.store.UserRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationService {
    AuthenticationDto register(UserRegisterDto user);

    AuthenticationDto login(UserLoginDto user);

    void logout(User user);
}
