package com.works.metrostation.service;

import com.works.metrostation.dto.CardDto;
import com.works.metrostation.dto.CardMoneyDto;
import com.works.metrostation.dto.UserDetailsDto;
import com.works.metrostation.model.Card;

public interface CardService {

    Card createCard(CardDto cardDto);

    Card getCard(UserDetailsDto userDetailsDto, String cardNumber);

    Card loadingMoneyOnCard(CardMoneyDto cardMoneyDto);

    Card deleteCard(String cardNumber);

}
