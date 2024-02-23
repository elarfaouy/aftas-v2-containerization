package com.youcode.aftas.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UpdateLevelDto implements Serializable {
    @NotNull(message = "code cannot be null.")
    @Positive(message = "code cannot be negative.")
    private Integer code;

    @NotNull(message = "points cannot be null.")
    @Positive(message = "points cannot be negative.")
    private Integer points;

    @NotBlank(message = "description cannot be blank.")
    private String description;
}