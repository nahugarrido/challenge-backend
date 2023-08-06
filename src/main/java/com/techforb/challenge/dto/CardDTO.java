package com.techforb.challenge.dto;

import com.techforb.challenge.enums.CardType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardDTO {
    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private LocalDate expirationDate;

    private CardType cardType;

    private String cvv;
}
