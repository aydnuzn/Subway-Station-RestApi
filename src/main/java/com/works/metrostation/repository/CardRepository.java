package com.works.metrostation.repository;

import com.works.metrostation.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardNumberEquals(String cardNumber);

    Optional<Card> findByUserIdEquals(Long userId);
}
