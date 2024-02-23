package com.youcode.aftas.web.rest;

import com.youcode.aftas.dto.payload.FishDto;
import com.youcode.aftas.service.IFishService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fish")
public class FishRest {
    private final IFishService fishService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_FISH')")
    public List<FishDto> getAll() {
        return fishService.findAll();
    }
}
