package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.Competition;
import com.youcode.aftas.domain.entity.Hunting;
import com.youcode.aftas.domain.entity.Ranking;
import com.youcode.aftas.domain.enums.CompetitionStatus;
import com.youcode.aftas.dto.payload.CompetitionDto;
import com.youcode.aftas.dto.payload.MemberDto;
import com.youcode.aftas.dto.payload.RankingDto;
import com.youcode.aftas.dto.store.StoreCompetitionDto;
import com.youcode.aftas.exception.DataBaseConstraintException;
import com.youcode.aftas.exception.LogicValidationException;
import com.youcode.aftas.repository.CompetitionRepository;
import com.youcode.aftas.repository.HuntingRepository;
import com.youcode.aftas.repository.RankingRepository;
import com.youcode.aftas.service.ICompetitionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompetitionService implements ICompetitionService {
    private final CompetitionRepository repository;
    private final RankingRepository rankingRepository;
    private final HuntingRepository huntingRepository;
    private final ModelMapper mapper;

    @Override
    public Page<CompetitionDto> findAll(int page, int size) {
        Page<Competition> competitionPage = repository.findAll(PageRequest.of(page, size));
        return competitionPage.map((element) -> {
            CompetitionDto competitionDto = mapper.map(element, CompetitionDto.class);
            competitionDto.setStatus(calculateCompetitionStatus(element.getDate(), element.getStartTime(), element.getEndTime()));
            return competitionDto;
        });
    }

    @Override
    public Optional<CompetitionDto> findOne(String code) {
        Optional<Competition> optional = repository.findById(code);
        return optional.map(element -> {
            CompetitionDto competitionDto = mapper.map(element, CompetitionDto.class);
            competitionDto.setStatus(calculateCompetitionStatus(element.getDate(), element.getStartTime(), element.getEndTime()));
            return competitionDto;
        });
    }

    @Override
    public CompetitionDto store(StoreCompetitionDto storeCompetitionDto) {

        if (!codeMatchFormattedCode(storeCompetitionDto.getCode(), storeCompetitionDto.getLocation(), storeCompetitionDto.getDate())) {
            throw new LogicValidationException("invalid code value, code must be like ex: imsouane: pattern: ims-22-12-23.");
        }

        if (!storeCompetitionDto.getEndTime().isAfter(storeCompetitionDto.getStartTime())) {
            throw new LogicValidationException("invalid time, end time must be after start.");
        }

        if (!(Duration.between(storeCompetitionDto.getStartTime(), storeCompetitionDto.getEndTime()).toHours() > 2)) {
            throw new LogicValidationException("invalid time, duration must be greater than 2 hours.");
        }

        if (repository.existsByDate(storeCompetitionDto.getDate())) {
            throw new LogicValidationException("invalid date, its allowed only one competition in a day.");
        }

        Competition competition = mapper.map(storeCompetitionDto, Competition.class);
        Competition saved = repository.save(competition);
        return mapper.map(saved, CompetitionDto.class);
    }

    @Override
    public List<RankingDto> calculateScore(String code) {
        Optional<Competition> optionalCompetition = repository.findById(code);
        if (optionalCompetition.isEmpty()) {
            throw new DataBaseConstraintException("their is no competition with that code.");
        }

        List<Ranking> rankings = rankingRepository.findByCompetition(optionalCompetition.get());

        List<Ranking> rankingsAfterCalcScore = rankings
                .stream()
                .peek(ranking -> {
                    List<Hunting> userHunting = huntingRepository.findByCompetitionAndMember(ranking.getCompetition(), ranking.getMember());
                    Integer huntScore = userHunting
                            .stream()
                            .map(hunting -> hunting.getFish().getLevel().getPoints() * hunting.getNumberOfFish())
                            .mapToInt(Integer::intValue)
                            .sum();

                    ranking.setScore(huntScore);
                })
                .sorted(Comparator.comparingInt(Ranking::getScore).reversed())
                .toList();

        List<Ranking> finalRanking = rankingsAfterCalcScore.stream().peek(ranking -> ranking.setRank(rankingsAfterCalcScore.indexOf(ranking) + 1)).toList();

        rankingRepository.saveAll(rankings);

        return finalRanking
                .stream()
                .map((element) -> {
                    MemberDto memberDto = mapper.map(element.getMember(), MemberDto.class);
                    CompetitionDto competitionDto = mapper.map(element.getCompetition(), CompetitionDto.class);
                    return RankingDto.builder()
                            .rank(element.getRank())
                            .score(element.getScore())
                            .member(memberDto)
                            .competition(competitionDto)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private Boolean codeMatchFormattedCode(String code, String location, LocalDate date) {
        String formattedLocation = location.substring(0, Math.min(3, location.length()));
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yy"));

        String formattedCode = formattedLocation + "-" + formattedDate;
        return code.equals(formattedCode);
    }

    private CompetitionStatus calculateCompetitionStatus(LocalDate date, LocalTime startTime, LocalTime endTime) {
        LocalDate now = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        if (now.isAfter(date) || (now.isEqual(date) && timeNow.isAfter(endTime))) {
            return CompetitionStatus.COMPLETED;
        } else if (now.isBefore(date) || (now.isEqual(date) && timeNow.isBefore(startTime))) {
            return CompetitionStatus.UPCOMING;
        } else {
            return CompetitionStatus.ONGOING;
        }
    }
}
