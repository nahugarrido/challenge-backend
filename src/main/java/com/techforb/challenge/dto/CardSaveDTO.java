package com.techforb.challenge.dto;

import com.techforb.challenge.enums.CardType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardSaveDTO {

    @NotEmpty
    private String userID;

    @NotNull
    private LocalDate date;

    @NotNull
    private CardType cardType;
}
