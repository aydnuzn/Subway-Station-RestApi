package com.works.metrostation.service;

import com.works.metrostation.dto.CardDto;
import com.works.metrostation.dto.CardMoneyDto;
import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.model.Card;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.CardRepository;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @InjectMocks
    private CardServiceImpl cardServiceImpl;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    User RECORD_USER1 = new User(1L,"admin@mail.com", "123456", UserRole.ADMIN);
    User RECORD_USER2 = new User(2L,"yolcu@mail.com", "123456",UserRole.PASSANGER);

    Card RECORD_CARD = new Card(1L, "11223344", 0F, 2L);

    @Test
    public void getCardById_success() throws Exception{

        UserDetailsDto userDetailsDto = new UserDetailsDto(RECORD_USER1);

        Mockito.when(cardRepository.findByCardNumberEquals(RECORD_CARD.getCardNumber()))
                .thenReturn(Optional.of(RECORD_CARD));

        assertEquals(cardServiceImpl.getCard(userDetailsDto, RECORD_CARD.getCardNumber()), RECORD_CARD);
    }

    @Test
    public void createCardById_sucess() throws Exception{

        CardDto cardDto = new CardDto(RECORD_CARD.getCardNumber(), RECORD_CARD.getUserId());

        Mockito.when(userRepository.findById(RECORD_CARD.getUserId()))
                .thenReturn(Optional.of(RECORD_USER2));

        Card card = RECORD_CARD;
        card.setId(null);

        Mockito.when(cardRepository.save(card)).thenReturn(card);

        assertEquals(cardServiceImpl.createCard(cardDto), card);
    }

    @Test
    public void deleteCardById_success() throws Exception{

        Mockito.when(cardRepository.findByCardNumberEquals(RECORD_CARD.getCardNumber()))
                .thenReturn(Optional.of(RECORD_CARD));

        assertEquals(cardServiceImpl.deleteCard(RECORD_CARD.getCardNumber()), RECORD_CARD);
    }

    @Test
    public void loadingMoneyOnCard_success() throws Exception{

        CardMoneyDto cardMoneyDto = new CardMoneyDto(RECORD_CARD.getCardNumber(), 5F);

        Mockito.when(cardRepository.findByCardNumberEquals(RECORD_CARD.getCardNumber()))
                .thenReturn(Optional.of(RECORD_CARD));

        Card card = RECORD_CARD;
        card.setBalance(cardMoneyDto.getBalance() + RECORD_CARD.getBalance());

        Mockito.when(cardRepository.saveAndFlush(card)).thenReturn(card);

        assertEquals(cardServiceImpl.loadingMoneyOnCard(cardMoneyDto), card);
    }

}
