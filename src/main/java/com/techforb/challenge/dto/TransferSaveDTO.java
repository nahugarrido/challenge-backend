package com.techforb.challenge.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferSaveDTO {
    @NotEmpty
    private String userSender;

    @NotEmpty
    private String userDestinatary;

    @NotNull
    private BigDecimal amount;
}
