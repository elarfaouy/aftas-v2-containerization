package com.youcode.aftas.dto.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RankingDto {
    private Integer rank;
    private Integer score;
    private CompetitionDto competition;
    private MemberDto member;
}
