package com.youcode.aftas.repository;

import com.youcode.aftas.domain.entity.Competition;
import com.youcode.aftas.domain.entity.Ranking;
import com.youcode.aftas.domain.entity.RankingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, RankingKey> {
    List<Ranking> findByCompetition(Competition competition);
}
