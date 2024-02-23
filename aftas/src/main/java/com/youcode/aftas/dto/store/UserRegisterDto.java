package com.youcode.aftas.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRegisterDto implements Serializable {
    @NotNull(message = "num cannot be null.")
    @Positive(message = "num cannot be negative.")
    private Integer num;

    @NotNull(message = "name cannot be null.")
    @NotBlank(message = "name cannot be blank.")
    private String name;

    @NotNull(message = "family name cannot be null.")
    @NotBlank(message = "family name cannot be blank.")
    private String familyName;

    @NotNull(message = "username cannot be null.")
    @NotBlank(message = "username cannot be blank.")
    private String username;

    @NotNull(message = "password cannot be null.")
    @NotBlank(message = "password cannot be blank.")
    private String password;
}
