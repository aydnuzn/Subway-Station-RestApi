package com.works.metrostation.service.impl;

import com.works.metrostation.dto.BoardingResponseDto;
import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.exception.CardNotFoundException;
import com.works.metrostation.model.Card;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.CardRepository;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.MetroStationService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class MetroStationServiceImpl implements MetroStationService {

    private final static Float ENTRANCE_FEE = 3.50f;

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public MetroStationServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BoardingResponseDto entranceToSubway(UserDetailsDto userDetailsDto){

        User user = userRepository.findById(userDetailsDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        Card card = cardRepository.findByUserIdEquals(userDetailsDto.getUser().getId())
                .orElseThrow(() -> new CardNotFoundException("User's Card Not Found"));

        if(card.getBalance() < ENTRANCE_FEE){
            throw new EntityNotFoundException("Not Enought Balance");
        }

        card.setBalance(card.getBalance() - ENTRANCE_FEE);
        cardRepository.saveAndFlush(card);

        BoardingResponseDto boardingResponseDto = BoardingResponseDto.builder()
                .moneyPaid(ENTRANCE_FEE)
                .user(user)
                .card(card)
                .build();

        return boardingResponseDto;
    }

}
