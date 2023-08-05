package com.techforb.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanSaveDTO {

    private String userID;

    private BigDecimal amount;

    private LocalDate date;

    private int totalInstallments;

    private double interestRate;
}
