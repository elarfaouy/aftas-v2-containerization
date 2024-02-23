package com.youcode.aftas.repository;

import com.youcode.aftas.domain.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, String> {
    Boolean existsByDate(LocalDate date);
}
