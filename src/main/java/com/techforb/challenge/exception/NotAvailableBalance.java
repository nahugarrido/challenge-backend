package com.techforb.challenge.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class NotAvailableBalance extends GenericException {
    private BigDecimal balance;

    public NotAvailableBalance(HttpStatus httpStatus, BigDecimal balance) {
        super("", httpStatus);
        this.balance = balance;
    }

    @Override
    public String getMessage() {
        return "Insufficient funds, your current balance is $" + balance + ".";
    }
}
