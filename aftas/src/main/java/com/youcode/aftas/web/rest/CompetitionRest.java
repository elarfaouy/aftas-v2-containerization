package com.youcode.aftas.web.rest;

import com.youcode.aftas.dto.payload.CompetitionDto;
import com.youcode.aftas.dto.payload.RankingDto;
import com.youcode.aftas.dto.store.StoreCompetitionDto;
import com.youcode.aftas.service.ICompetitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/competition")
public class CompetitionRest {
    private final ICompetitionService competitionService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_COMPETITION')")
    public Page<CompetitionDto> getAll(@RequestParam int page, @RequestParam int size) {
        return competitionService.findAll(page, size);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAuthority('READ_COMPETITION')")
    public ResponseEntity<CompetitionDto> getOne(@PathVariable String code) {
        Optional<CompetitionDto> competitionDto = competitionService.findOne(code);
        return competitionDto.map(dto -> ResponseEntity.ok().body(dto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_COMPETITION')")
    public CompetitionDto save(@Valid @RequestBody StoreCompetitionDto storeCompetitionDto) {
        return competitionService.store(storeCompetitionDto);
    }

    @GetMapping("/score/{code}")
    @PreAuthorize("hasAuthority('READ_COMPETITION')")
    public List<RankingDto> calculateScore(@PathVariable String code) {
        return competitionService.calculateScore(code);
    }
}
