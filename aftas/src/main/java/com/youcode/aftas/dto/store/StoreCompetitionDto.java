package com.youcode.aftas.dto.store;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class StoreCompetitionDto implements Serializable {
    @NotNull(message = "code cannot be null.")
    @NotBlank(message = "code cannot be blank.")
    private String code;

    @NotNull(message = "date cannot be null.")
    @Future(message = "date cannot be in the past.")
    private LocalDate date;

    @NotNull(message = "start time cannot be null.")
    private LocalTime startTime;

    @NotNull(message = "end time cannot be null.")
    private LocalTime endTime;

    @NotNull(message = "location cannot be null.")
    @NotBlank(message = "location cannot be blank.")
    private String location;

    @NotNull(message = "amount cannot be null.")
    @PositiveOrZero(message = "amount cannot be negative.")
    private Double amount;
}