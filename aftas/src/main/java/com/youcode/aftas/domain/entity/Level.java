package com.youcode.aftas.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;
    private Integer points;
    private String description;

    @OneToMany(mappedBy = "level")
    @ToString.Exclude
    private List<Fish> fishList;
}
