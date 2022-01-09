package com.works.metrostation.service.impl;

import com.works.metrostation.dto.CardDto;
import com.works.metrostation.dto.CardMoneyDto;
import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.exception.AccessDeniedException;
import com.works.metrostation.exception.CardAlreadyExistsException;
import com.works.metrostation.exception.EntityNotFoundException;
import com.works.metrostation.model.Card;
import com.works.metrostation.repository.CardRepository;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.CardService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Card createCard(CardDto cardDto){

        userRepository.findById(cardDto.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        Card card = Card.builder()
                .cardNumber(cardDto.getCardNumber())
                .balance(0f)
                .userId(cardDto.getUser_id())
                .build();

        try {
            card = cardRepository.save(card);
        } catch (DataIntegrityViolationException ex) {
            throw new CardAlreadyExistsException("The card number has to be unique and The Passenger can have at most one card.");
        }
        return card;

    }

    @Override
    public Card getCard(UserDetailsDto userDetailsDto, String cardNumber){

        Card card = cardRepository.findByCardNumberEquals(cardNumber)
                        .orElseThrow(() -> new EntityNotFoundException("Card Not Found"));
        checkUserPermission(userDetailsDto, card.getUserId());
        return card;
    }

    @Override
    public Card loadingMoneyOnCard(CardMoneyDto cardMoneyDto){

        Card card = cardRepository.findByCardNumberEquals(cardMoneyDto.getCardNumber())
                .orElseThrow(() -> new EntityNotFoundException("Card Not Found"));
        card.setBalance(card.getBalance() + cardMoneyDto.getBalance());
        return cardRepository.saveAndFlush(card);
    }

    @Override
    public Card deleteCard(String cardNumber){

        Card card = cardRepository.findByCardNumberEquals(cardNumber)
                .orElseThrow(() -> new EntityNotFoundException("Card Not Found"));
        cardRepository.delete(card);
        return card;
    }

    private static void checkUserPermission(UserDetailsDto userDetailsDto, Long userId) {
        if (!userDetailsDto.getUser().getId().equals(userId) && !userDetailsDto.getUser().getRole().equals(UserRole.ADMIN)) {
            throw new AccessDeniedException("Access Denied");
        }
    }

}
