package com.youcode.aftas.repository;

import com.youcode.aftas.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<User, Integer> {
}
