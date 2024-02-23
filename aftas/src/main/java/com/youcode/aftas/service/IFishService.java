package com.youcode.aftas.service;

import com.youcode.aftas.dto.payload.FishDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFishService {
    List<FishDto> findAll();
}
