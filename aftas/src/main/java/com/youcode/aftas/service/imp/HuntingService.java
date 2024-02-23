package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.*;
import com.youcode.aftas.domain.enums.CompetitionStatus;
import com.youcode.aftas.exception.DataBaseConstraintException;
import com.youcode.aftas.exception.LogicValidationException;
import com.youcode.aftas.repository.*;
import com.youcode.aftas.service.ICompetitionService;
import com.youcode.aftas.service.IHuntingService;
import com.youcode.aftas.dto.payload.CompetitionDto;
import com.youcode.aftas.dto.payload.HuntingDto;
import com.youcode.aftas.dto.store.StoreHuntingDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HuntingService implements IHuntingService {
    private final HuntingRepository repository;
    private final CompetitionRepository competitionRepository;
    private final MemberRepository memberRepository;
    private final FishRepository fishRepository;
    private final RankingRepository rankingRepository;
    private final ModelMapper mapper;

    private final ICompetitionService competitionService;

    @Override
    public HuntingDto store(StoreHuntingDto storeHuntingDto) {
        Optional<Competition> optionalCompetition = competitionRepository.findById(storeHuntingDto.getCompetition().getCode());
        if (optionalCompetition.isEmpty()) {
            throw new DataBaseConstraintException("their is no competition with that code.");
        }

        Optional<User> optionalMember = memberRepository.findById(storeHuntingDto.getMember().getNum());
        if (optionalMember.isEmpty()) {
            throw new DataBaseConstraintException("their is no member with that number.");
        }

        Optional<Fish> fishOptional = fishRepository.findById(storeHuntingDto.getFish().getName());
        if (fishOptional.isEmpty()) {
            throw new DataBaseConstraintException("their is no fish with that name.");
        }

        if (fishOptional.get().getAverageWeight() > storeHuntingDto.getFishWeight()) {
            throw new LogicValidationException("weight to small, the average weight for " + fishOptional.get().getName() + ", is " + fishOptional.get().getAverageWeight());
        }

        Hunting hunting = mapper.map(storeHuntingDto, Hunting.class);

        Optional<Ranking> optionalRanking = rankingRepository.findById(new RankingKey(hunting.getCompetition().getCode(), hunting.getMember().getNum()));
        if (optionalRanking.isEmpty()){
            throw new LogicValidationException("you are not registered to this competition.");
        }

        Optional<CompetitionDto> competitionDto = competitionService.findOne(hunting.getCompetition().getCode());
        if (!(competitionDto.isPresent() && competitionDto.get().getStatus().equals(CompetitionStatus.ONGOING))) {
            throw new LogicValidationException("you can store a hunt only when competition is ongoing.");
        }

        Optional<Hunting> existsHunting = repository.findByFishAndCompetitionAndMember(hunting.getFish(), hunting.getCompetition(), hunting.getMember());
        if (existsHunting.isPresent()) {
            Hunting hunt = existsHunting.get();
            hunt.setNumberOfFish(hunt.getNumberOfFish() + 1);
            Hunting merged = repository.save(hunt);
            return mapper.map(merged, HuntingDto.class);
        } else {
            hunting.setNumberOfFish(1);
            Hunting saved = repository.save(hunting);
            return mapper.map(saved, HuntingDto.class);
        }
    }

    @Override
    public List<HuntingDto> findByCompetition(String code) {
        Optional<Competition> optionalCompetition = competitionRepository.findById(code);
        if (optionalCompetition.isEmpty()) {
            throw new DataBaseConstraintException("their is no competition with that code.");
        }

        return repository
                .findByCompetition(optionalCompetition.get())
                .stream()
                .map((element) -> mapper.map(element, HuntingDto.class))
                .collect(Collectors.toList());
    }
}
