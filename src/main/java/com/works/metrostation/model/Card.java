package com.works.metrostation.model;

import com.works.metrostation.enumeration.CardType;
import com.works.metrostation.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, unique = true, length = 20)
    private String cardNumber;

    @Column(nullable = false)
    private Float balance;

    @Enumerated(EnumType.STRING)
    public CardType cardType;

    @Column(nullable = false, unique = true)
    private Long userId;
}
