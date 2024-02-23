package com.youcode.aftas.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Hunting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer numberOfFish;

    @ManyToOne
    private Fish fish;

    @ManyToOne
    private User member;

    @ManyToOne
    private Competition competition;
}
