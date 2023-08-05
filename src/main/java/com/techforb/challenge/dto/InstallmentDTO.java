package com.techforb.challenge.dto;

import com.techforb.challenge.enums.InstallmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InstallmentDTO {
    private Long id;

    private String number;

    private BigDecimal totalAmount;

    private BigDecimal amount;

    private LocalDate date;

    private double interestRate;

    private InstallmentStatus status;
}
