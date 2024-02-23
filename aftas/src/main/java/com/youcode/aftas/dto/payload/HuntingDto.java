package com.youcode.aftas.dto.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HuntingDto implements Serializable {
    private Integer id;
    private Integer numberOfFish;
    private FishDto fish;
    private MemberDto member;
    private CompetitionDto competition;
}