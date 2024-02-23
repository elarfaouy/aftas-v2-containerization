package com.youcode.aftas.dto.store;

import com.youcode.aftas.dto.update.UpdateMemberDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StoreRankingDto implements Serializable {
    @NotNull(message = "rank cannot be null.")
    private Integer rank;

    @NotNull(message = "score cannot be null.")
    @PositiveOrZero(message = "score cannot be negative.")
    private Integer score;

    @NotNull(message = "competition cannot be null.")
    private StoreCompetitionDto competition;

    @NotNull(message = "member cannot be null.")
    private UpdateMemberDto member;
}