package com.youcode.aftas.service;

import com.youcode.aftas.dto.payload.HuntingDto;
import com.youcode.aftas.dto.store.StoreHuntingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IHuntingService {
    HuntingDto store(StoreHuntingDto storeHuntingDto);

    List<HuntingDto> findByCompetition(String code);
}
