package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.*;
import com.youcode.aftas.dto.payload.RankingDto;
import com.youcode.aftas.exception.DataBaseConstraintException;
import com.youcode.aftas.repository.CompetitionRepository;
import com.youcode.aftas.repository.HuntingRepository;
import com.youcode.aftas.repository.RankingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CompetitionServiceTest {
    private final List<Ranking> rankings = List.of(
            Ranking.builder().id(new RankingKey("ims-24-12-23", 1234)).rank(0).score(0).competition(Competition.builder().code("ims-24-12-23").build()).member(User.builder().num(1234).build()).build(),
            Ranking.builder().id(new RankingKey("ims-24-12-23", 5678)).rank(1).score(100).competition(Competition.builder().code("ims-24-12-23").build()).member(User.builder().num(5678).build()).build(),
            Ranking.builder().id(new RankingKey("ims-24-12-23", 9876)).rank(2).score(50).competition(Competition.builder().code("ims-24-12-23").build()).member(User.builder().num(9876).build()).build(),
            Ranking.builder().id(new RankingKey("ims-24-12-23", 4321)).rank(3).score(75).competition(Competition.builder().code("ims-24-12-23").build()).member(User.builder().num(4321).build()).build(),

            Ranking.builder().id(new RankingKey("ims-25-12-23", 1111)).rank(0).score(0).competition(Competition.builder().code("ims-25-12-23").build()).member(User.builder().num(1111).build()).build(),
            Ranking.builder().id(new RankingKey("ims-25-12-23", 2222)).rank(0).score(0).competition(Competition.builder().code("ims-25-12-23").build()).member(User.builder().num(2222).build()).build(),
            Ranking.builder().id(new RankingKey("ims-25-12-23", 3333)).rank(0).score(0).competition(Competition.builder().code("ims-25-12-23").build()).member(User.builder().num(3333).build()).build(),
            Ranking.builder().id(new RankingKey("ims-25-12-23", 4444)).rank(0).score(0).competition(Competition.builder().code("ims-25-12-23").build()).member(User.builder().num(4444).build()).build(),

            Ranking.builder().id(new RankingKey("ims-26-12-23", 5555)).rank(8).score(85).competition(Competition.builder().code("ims-26-12-23").build()).member(User.builder().num(5555).build()).build(),
            Ranking.builder().id(new RankingKey("ims-26-12-23", 6666)).rank(9).score(95).competition(Competition.builder().code("ims-26-12-23").build()).member(User.builder().num(6666).build()).build(),
            Ranking.builder().id(new RankingKey("ims-26-12-23", 7777)).rank(10).score(55).competition(Competition.builder().code("ims-26-12-23").build()).member(User.builder().num(7777).build()).build(),
            Ranking.builder().id(new RankingKey("ims-26-12-23", 8888)).rank(11).score(65).competition(Competition.builder().code("ims-26-12-23").build()).member(User.builder().num(8888).build()).build(),

            Ranking.builder().id(new RankingKey("ims-27-12-23", 9999)).rank(12).score(75).competition(Competition.builder().code("ims-27-12-23").build()).member(User.builder().num(9999).build()).build(),
            Ranking.builder().id(new RankingKey("ims-27-12-23", 1010)).rank(13).score(80).competition(Competition.builder().code("ims-27-12-23").build()).member(User.builder().num(1010).build()).build(),
            Ranking.builder().id(new RankingKey("ims-27-12-23", 1111)).rank(14).score(90).competition(Competition.builder().code("ims-27-12-23").build()).member(User.builder().num(1111).build()).build(),
            Ranking.builder().id(new RankingKey("ims-27-12-23", 1212)).rank(15).score(70).competition(Competition.builder().code("ims-27-12-23").build()).member(User.builder().num(1212).build()).build(),

            Ranking.builder().id(new RankingKey("ims-28-12-23", 1313)).rank(16).score(65).competition(Competition.builder().code("ims-28-12-23").build()).member(User.builder().num(1313).build()).build(),
            Ranking.builder().id(new RankingKey("ims-28-12-23", 1414)).rank(17).score(75).competition(Competition.builder().code("ims-28-12-23").build()).member(User.builder().num(1414).build()).build(),
            Ranking.builder().id(new RankingKey("ims-28-12-23", 1515)).rank(18).score(85).competition(Competition.builder().code("ims-28-12-23").build()).member(User.builder().num(1515).build()).build(),
            Ranking.builder().id(new RankingKey("ims-28-12-23", 1616)).rank(19).score(95).competition(Competition.builder().code("ims-28-12-23").build()).member(User.builder().num(1616).build()).build()
    );

    private final List<List<Hunting>> huntingList = List.of(
            List.of(
                    Hunting.builder().numberOfFish(2).fish(Fish.builder().level(Level.builder().points(1000).build()).build()).build(),
                    Hunting.builder().numberOfFish(1).fish(Fish.builder().level(Level.builder().points(3000).build()).build()).build(),
                    Hunting.builder().numberOfFish(2).fish(Fish.builder().level(Level.builder().points(1500).build()).build()).build()
            ),
            List.of(
                    Hunting.builder().numberOfFish(3).fish(Fish.builder().level(Level.builder().points(1200).build()).build()).build(),
                    Hunting.builder().numberOfFish(2).fish(Fish.builder().level(Level.builder().points(2500).build()).build()).build(),
                    Hunting.builder().numberOfFish(1).fish(Fish.builder().level(Level.builder().points(1800).build()).build()).build()
            ),
            List.of(
                    Hunting.builder().numberOfFish(1).fish(Fish.builder().level(Level.builder().points(800).build()).build()).build(),
                    Hunting.builder().numberOfFish(3).fish(Fish.builder().level(Level.builder().points(2000).build()).build()).build(),
                    Hunting.builder().numberOfFish(2).fish(Fish.builder().level(Level.builder().points(1200).build()).build()).build()
            ),
            List.of(
                    Hunting.builder().numberOfFish(2).fish(Fish.builder().level(Level.builder().points(900).build()).build()).build(),
                    Hunting.builder().numberOfFish(1).fish(Fish.builder().level(Level.builder().points(2800).build()).build()).build(),
                    Hunting.builder().numberOfFish(3).fish(Fish.builder().level(Level.builder().points(1600).build()).build()).build()
            )
    );


    @InjectMocks
    CompetitionService service;
    @Mock
    CompetitionRepository competitionRepository;
    @Mock
    RankingRepository rankingRepository;
    @Mock
    HuntingRepository huntingRepository;
    @Mock
    ModelMapper mapper;

    @Test
    void throwExceptionForInvalidCompetition() {
        Mockito.doReturn(Optional.empty()).when(competitionRepository).findById(any());

        assertThrowsExactly(DataBaseConstraintException.class, () -> {
            List<RankingDto> score = service.calculateScore("ims-30-12-23");
        });
    }

    @Test
    void checkSoreListNotNull() {
        String code = "ims-25-12-23";
        List<Ranking> list = filterRankings(code);

        Mockito.doReturn(Optional.of(Competition.builder().build())).when(competitionRepository).findById(any());
        Mockito.doReturn(list).when(rankingRepository).findByCompetition(any());

        List<RankingDto> score = service.calculateScore(code);

        assertNotNull(score);
    }

    @Test
    void checkScoreListSameLength() {
        String code = "ims-25-12-23";
        List<Ranking> list = filterRankings(code);

        Mockito.doReturn(Optional.of(Competition.builder().build())).when(competitionRepository).findById(any());
        Mockito.doReturn(list).when(rankingRepository).findByCompetition(any());

        List<RankingDto> score = service.calculateScore(code);

        assertEquals(list.size(), score.size());
    }

    @Test
    void checkRightListRank() {
        String code = "ims-25-12-23";
        List<Ranking> list = filterRankings(code);

        Mockito.doReturn(Optional.of(Competition.builder().build())).when(competitionRepository).findById(any());
        Mockito.doReturn(list).when(rankingRepository).findByCompetition(any());

        Mockito.doReturn(huntingList.get(0)).when(huntingRepository).findByCompetitionAndMember(list.get(0).getCompetition(), list.get(0).getMember());
        Mockito.doReturn(huntingList.get(1)).when(huntingRepository).findByCompetitionAndMember(list.get(1).getCompetition(), list.get(1).getMember());
        Mockito.doReturn(huntingList.get(2)).when(huntingRepository).findByCompetitionAndMember(list.get(2).getCompetition(), list.get(2).getMember());
        Mockito.doReturn(huntingList.get(3)).when(huntingRepository).findByCompetitionAndMember(list.get(3).getCompetition(), list.get(3).getMember());

        List<RankingDto> score = service.calculateScore(code);

        assertEquals(1, score.get(0).getRank());
        assertEquals(10400, score.get(0).getScore());

        assertEquals(2, score.get(1).getRank());
        assertEquals(9400, score.get(1).getScore());

        assertEquals(3, score.get(2).getRank());
        assertEquals(9200, score.get(2).getScore());

        assertEquals(4, score.get(3).getRank());
        assertEquals(8000, score.get(3).getScore());
    }

    private List<Ranking> filterRankings(String code) {
        return rankings.stream().filter(ranking -> ranking.getId().getCode().equals(code)).collect(Collectors.toList());
    }
}