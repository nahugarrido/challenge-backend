package com.techforb.challenge.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanSaveDTO {

    @NotEmpty
    private String userID;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotNull
    private int totalInstallments;

    @NotNull
    private double interestRate;
}
