package com.works.metrostation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.metrostation.dto.CardDto;
import com.works.metrostation.dto.CardMoneyDto;
import com.works.metrostation.enumeration.CardType;
import com.works.metrostation.model.Card;
import com.works.metrostation.repository.UserRepository;
import com.works.metrostation.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
@WithMockUser(value = "admin@mail.com", authorities = {"ADMIN"})
public class CardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    Card RECORD_CARD = new Card(1L, "11223344", 20F, CardType.STANDARD, 2L);

    @Test
    public void getCardByNumber_success() throws Exception {

        mockMvc.perform(get("/cards/{cardNumber}",11223344)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void createCardByNumber_success() throws Exception {

        CardDto cardDto = new CardDto(RECORD_CARD.getCardNumber(), RECORD_CARD.getCardType(), RECORD_CARD.getUserId());

        mockMvc.perform(post("/cards")
                        .content(mapper.writeValueAsString(cardDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCardByNumber_success() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cards/{cardNumber}",11223344)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void loadingMoneyOnCard_success() throws Exception {

        CardMoneyDto cardMoneyDto = new CardMoneyDto(RECORD_CARD.getCardNumber(), RECORD_CARD.getBalance());

        mockMvc.perform(put("/cards",1)
                        .content(mapper.writeValueAsString(cardMoneyDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
