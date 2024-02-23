package com.youcode.aftas.repository;

import com.youcode.aftas.domain.entity.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, String> {
}
