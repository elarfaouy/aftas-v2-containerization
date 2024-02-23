package com.youcode.aftas.dto.update;

import com.youcode.aftas.dto.store.StoreCompetitionDto;
import com.youcode.aftas.dto.store.StoreFishDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UpdateHuntingDto implements Serializable {
    @NotNull(message = "id cannot be null.")
    @Positive(message = "id cannot be negative.")
    private Integer id;

    @NotNull(message = "number of fish cannot be null.")
    @PositiveOrZero(message = "number of fish cannot be negative.")
    private Integer numberOfFish;

    @NotNull(message = "fish cannot be null.")
    private StoreFishDto fish;

    @NotNull(message = "member cannot be null.")
    private UpdateMemberDto member;

    @NotNull(message = "competition cannot be null.")
    private StoreCompetitionDto competition;
}