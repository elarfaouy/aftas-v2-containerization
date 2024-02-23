package com.youcode.aftas.dto.payload;

import com.youcode.aftas.domain.enums.CompetitionStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CompetitionDto implements Serializable {
    private String code;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private Double amount;
    private CompetitionStatus status;
}