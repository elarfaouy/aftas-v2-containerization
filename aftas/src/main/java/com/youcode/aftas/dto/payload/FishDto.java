package com.youcode.aftas.dto.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FishDto implements Serializable {
    private String name;
    private Double averageWeight;
    private LevelDto level;
}