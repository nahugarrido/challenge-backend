package com.techforb.challenge.dto;

import com.techforb.challenge.enums.InstallmentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InstallmentDTO {

    @NotNull
    private Long id;

    @NotEmpty
    private String number;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @NotNull
    private double interestRate;

    @NotNull
    private InstallmentStatus status;
}
