package com.techforb.challenge.dto;

import com.techforb.challenge.enums.CardType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardDTO {

    @NotNull
    private Long id;

    @NotEmpty
    private String cardNumber;

    @NotEmpty
    private String cardHolderName;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private CardType cardType;

    @NotEmpty
    private String cvv;
}
