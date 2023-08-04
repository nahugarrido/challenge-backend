package com.techforb.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferSaveDTO {
    private String userSender;
    private String userDestinatary;
    private BigDecimal amount;
}
