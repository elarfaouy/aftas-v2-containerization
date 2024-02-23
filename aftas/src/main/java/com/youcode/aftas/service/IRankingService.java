package com.youcode.aftas.service;

import com.youcode.aftas.dto.payload.RankingDto;
import com.youcode.aftas.dto.store.StoreRankingDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRankingService {
    List<RankingDto> findAll();
    StoreRankingDto registerMember(StoreRankingDto storeRankingDto);
}
