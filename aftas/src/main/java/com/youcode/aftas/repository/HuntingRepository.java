package com.youcode.aftas.repository;

import com.youcode.aftas.domain.entity.Competition;
import com.youcode.aftas.domain.entity.Fish;
import com.youcode.aftas.domain.entity.Hunting;
import com.youcode.aftas.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting, Integer> {
    Optional<Hunting> findByFishAndCompetitionAndMember(Fish fish, Competition competition, User member);
    List<Hunting> findByCompetitionAndMember(Competition competition, User member);
    List<Hunting> findByCompetition(Competition competition);
}
