package com.foodApp.repository;

import com.foodApp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByEmail(String email);

    Optional<Token> findByVerificationToken(String verificationToken);
}
