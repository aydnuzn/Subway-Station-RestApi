package com.works.metrostation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
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
    @Column(nullable = false)
    private Long user_id;

}
