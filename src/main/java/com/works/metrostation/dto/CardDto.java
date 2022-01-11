package com.works.metrostation.dto;

import com.works.metrostation.enumeration.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CardDto {

    @NotEmpty
    @Size(min = 4, max = 20)
    private String cardNumber;

    @NotNull
    private CardType cardType;

    @NotNull
    private Long user_id;
}
