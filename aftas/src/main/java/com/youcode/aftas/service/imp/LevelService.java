package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.Level;
import com.youcode.aftas.exception.LogicValidationException;
import com.youcode.aftas.repository.LevelRepository;
import com.youcode.aftas.service.ILevelService;
import com.youcode.aftas.dto.payload.LevelDto;
import com.youcode.aftas.dto.store.StoreLevelDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelService implements ILevelService {
    private final LevelRepository repository;
    private final ModelMapper mapper;

    @Override
    public LevelDto store(StoreLevelDto storeLevelDto) {
        Optional<Level> maxLevel = repository
                .findAll()
                .stream()
                .max(Comparator.comparingInt(Level::getPoints));

        if (maxLevel.isPresent() && maxLevel.get().getPoints() > storeLevelDto.getPoints()){
            throw new LogicValidationException("invalid points, must be greater than max one.");
        }

        Level level = mapper.map(storeLevelDto, Level.class);
        Level saved = repository.save(level);
        return mapper.map(saved, LevelDto.class);
    }
}
