package com.youcode.aftas.repository;

import com.youcode.aftas.domain.entity.Token;
import com.youcode.aftas.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);

    Optional<Token> findByToken(String token);

    Optional<Token> findByUuid(UUID uuid);
}
