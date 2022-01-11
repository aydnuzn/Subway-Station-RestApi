package com.works.metrostation.service;

import com.works.metrostation.dto.BoardingResponseDto;
import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.enumeration.CardType;
import com.works.metrostation.enumeration.UserRole;
import com.works.metrostation.model.Card;
import com.works.metrostation.model.User;
import com.works.metrostation.repository.CardRepository;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.impl.MetroStationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MetroStationServiceTest {

    @InjectMocks
    private MetroStationServiceImpl metroStationServiceImpl;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    User RECORD_USER = new User(2L,"yolcu@mail.com", "123456",UserRole.PASSANGER);

    Card RECORD_CARD = new Card(1L, "11223344", 5F, CardType.STANDARD ,2L);

    @Test
    public void entranceToSubway_success() throws Exception{

        UserDetailsDto userDetailsDto = new UserDetailsDto(RECORD_USER);

        Mockito.when(userRepository.findById(userDetailsDto.getUser().getId()))
                .thenReturn(Optional.of(RECORD_USER));
        Mockito.when(cardRepository.findByUserIdEquals(userDetailsDto.getUser().getId()))
                .thenReturn(Optional.of(RECORD_CARD));

        Card card = new Card(1L, "11223344", 5F, CardType.STANDARD,2L);
        card.setBalance(card.getBalance() - card.getCardType().getPrice());

        Mockito.when(cardRepository.saveAndFlush(card)).thenReturn(card);

        BoardingResponseDto boardingResponseDto = new BoardingResponseDto(3.5F, RECORD_USER, card);

        assertEquals(metroStationServiceImpl.entranceToSubway(userDetailsDto), boardingResponseDto);
    }

}
