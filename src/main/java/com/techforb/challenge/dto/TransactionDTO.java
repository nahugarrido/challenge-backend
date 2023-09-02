package com.techforb.challenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techforb.challenge.enums.MovementType;
import com.techforb.challenge.enums.TransactionStatus;
import com.techforb.challenge.enums.TransactionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {

    @NotEmpty
    private String header;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private MovementType movementType;
}
