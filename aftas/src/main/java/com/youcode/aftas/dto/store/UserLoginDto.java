package com.youcode.aftas.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserLoginDto implements Serializable {
    @NotNull(message = "username cannot be null.")
    @NotBlank(message = "username cannot be blank.")
    private String username;

    @NotNull(message = "password cannot be null.")
    @NotBlank(message = "password cannot be blank.")
    private String password;
}
