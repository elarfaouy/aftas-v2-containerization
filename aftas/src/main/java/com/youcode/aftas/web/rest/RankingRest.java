package com.youcode.aftas.web.rest;

import com.youcode.aftas.dto.payload.RankingDto;
import com.youcode.aftas.dto.store.StoreRankingDto;
import com.youcode.aftas.service.IRankingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RankingRest {
    private final IRankingService rankingService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_RANKING')")
    public List<RankingDto> getAll() {
        return rankingService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_RANKING')")
    public StoreRankingDto registerMemberToCompetition(@Valid @RequestBody StoreRankingDto storeRankingDto) {
        return rankingService.registerMember(storeRankingDto);
    }
}
