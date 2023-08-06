package com.techforb.challenge.dto;

import com.techforb.challenge.enums.CardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardSaveDTO {
    private String userID;

    private LocalDate date;

    private CardType cardType;
}
