package com.youcode.aftas.service.imp;

import com.youcode.aftas.repository.FishRepository;
import com.youcode.aftas.service.IFishService;
import com.youcode.aftas.dto.payload.FishDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FishService implements IFishService {
    private final FishRepository repository;
    private final ModelMapper mapper;

    @Override
    public List<FishDto> findAll() {
        return repository
                .findAll()
                .stream()
                .map((element) -> mapper.map(element, FishDto.class))
                .collect(Collectors.toList());
    }
}
