package com.techforb.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techforb.challenge.enums.MovementType;
import com.techforb.challenge.enums.TransactionStatus;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private TransactionStatus status;

    private MovementType movementType;
}
