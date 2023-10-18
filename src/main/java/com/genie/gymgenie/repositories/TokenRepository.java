package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.Token;
import com.genie.gymgenie.models.enums.TokenType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUserEmailAndTokenType(String email, TokenType type);

    Optional<Token> findByTokenValueAndTokenType(String token, TokenType type);

    @Transactional
    @Modifying
    @Query("UPDATE GENIE_TOKENS t SET t.confirmed = ?2 WHERE t.tokenValue = ?1")
    void updateConfirmedAt(String tokenValue, LocalDateTime confirmedAt);
}
