package com.works.metrostation.dto;

import com.works.metrostation.model.Card;
import com.works.metrostation.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardingResponseDto {

    private  Float moneyPaid;

    private User user;

    private Card card;
}
