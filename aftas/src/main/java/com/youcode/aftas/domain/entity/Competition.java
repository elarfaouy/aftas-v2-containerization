package com.youcode.aftas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Competition {
    @Id
    private String code;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private Double amount;

    @OneToMany(mappedBy = "competition")
    @ToString.Exclude
    private List<Hunting> huntingList;

    @OneToMany(mappedBy = "competition")
    @ToString.Exclude
    private List<Ranking> rankingList;
}
