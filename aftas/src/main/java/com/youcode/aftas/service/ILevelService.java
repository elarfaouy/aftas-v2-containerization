package com.youcode.aftas.service;

import com.youcode.aftas.dto.payload.LevelDto;
import com.youcode.aftas.dto.store.StoreLevelDto;
import org.springframework.stereotype.Service;

@Service
public interface ILevelService {
    LevelDto store(StoreLevelDto storeLevelDto);
}
