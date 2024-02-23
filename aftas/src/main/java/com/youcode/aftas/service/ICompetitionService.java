package com.youcode.aftas.service;

import com.youcode.aftas.dto.payload.CompetitionDto;
import com.youcode.aftas.dto.payload.RankingDto;
import com.youcode.aftas.dto.store.StoreCompetitionDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICompetitionService {
    Page<CompetitionDto> findAll(int page, int size);

    Optional<CompetitionDto> findOne(String code);

    CompetitionDto store(StoreCompetitionDto storeCompetitionDto);

    List<RankingDto> calculateScore(String code);
}
