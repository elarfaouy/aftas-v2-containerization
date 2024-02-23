package com.youcode.aftas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Fish {
    @Id
    private String name;
    private Double averageWeight;

    @ManyToOne
    private Level level;

    @OneToMany(mappedBy = "fish")
    @ToString.Exclude
    private List<Hunting> huntingList;
}
