package com.youcode.aftas.dto.update;

import com.youcode.aftas.domain.entity.Role;
import com.youcode.aftas.domain.enums.IdentityDocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserDto implements Serializable {
    @NotNull(message = "num cannot be null.")
    @Positive(message = "num cannot be negative.")
    private Integer num;

    @NotNull(message = "name cannot be null.")
    private Role role;
}