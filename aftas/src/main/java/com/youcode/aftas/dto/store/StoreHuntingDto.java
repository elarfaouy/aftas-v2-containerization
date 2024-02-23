package com.youcode.aftas.dto.store;

import com.youcode.aftas.dto.update.UpdateMemberDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StoreHuntingDto implements Serializable {
    @NotNull(message = "fish weight cannot be null.")
    @Positive(message = "fish weight cannot be negative.")
    private Double fishWeight;

    @NotNull(message = "fish cannot be null.")
    private StoreFishDto fish;

    @NotNull(message = "member cannot be null.")
    private UpdateMemberDto member;

    @NotNull(message = "competition cannot be null.")
    private StoreCompetitionDto competition;
}