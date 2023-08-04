package com.techforb.challenge.dto;

import com.techforb.challenge.enums.MovementType;
import com.techforb.challenge.enums.TransactionState;
import com.techforb.challenge.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    private String header;

    private TransactionType transactionType;

    private BigDecimal amount;

    private LocalDateTime date;

    private TransactionState state;

    private MovementType movementType;
}
