package com.youcode.aftas.web.rest;

import com.youcode.aftas.dto.payload.MemberDto;
import com.youcode.aftas.dto.store.StoreMemberDto;
import com.youcode.aftas.dto.update.UpdateUserDto;
import com.youcode.aftas.service.IMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRest {
    private final IMemberService memberService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_MEMBER')")
    public List<MemberDto> getAll() {
        return memberService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_MEMBER')")
    public MemberDto save(@Valid @RequestBody StoreMemberDto storeMemberDto) {
        return memberService.store(storeMemberDto);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('WRITE_MEMBER')")
    public MemberDto update(@Valid @RequestBody UpdateUserDto updateUserDto) {
        return memberService.update(updateUserDto);
    }
}
