package com.youcode.aftas.web.rest;

import com.youcode.aftas.dto.payload.LevelDto;
import com.youcode.aftas.dto.store.StoreLevelDto;
import com.youcode.aftas.service.ILevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/level")
public class LevelRest {
    private final ILevelService levelService;

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_LEVEL')")
    public LevelDto save(@Valid @RequestBody StoreLevelDto storeLevelDto) {
        return levelService.store(storeLevelDto);
    }
}
