package com.youcode.aftas.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StoreLevelDto implements Serializable {
    @NotNull(message = "points cannot be null.")
    @Positive(message = "points cannot be negative.")
    private Integer points;

    @NotBlank(message = "description cannot be blank.")
    private String description;
}