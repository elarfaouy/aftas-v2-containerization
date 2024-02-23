package com.youcode.aftas.domain.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Ranking {
    @EmbeddedId
    private RankingKey id;
    private Integer rank;
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "code", insertable = false, updatable = false)
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "num", insertable = false, updatable = false)
    private User member;
}
