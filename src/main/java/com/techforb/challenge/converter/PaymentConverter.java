package com.techforb.challenge.converter;

import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.entity.Payment;
import com.techforb.challenge.enums.MovementType;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter extends AbstractConverter<Payment, TransactionDTO> {

    @Override
    protected TransactionDTO convert(Payment source) {
        TransactionDTO target = new TransactionDTO();

        target.setDate(source.getDate());
        target.setTransactionType(source.getType());
        target.setAmount(source.getAmount());
        target.setStatus(source.getStatus());
        target.setTransactionType(source.getType());
        target.setMovementType(MovementType.EXPENSE);

        int installmentNumber = source.getInstallment().getNumber();
        int totalInstallments = source.getInstallment().getLoan().getTotalInstallments();
        target.setHeader("Cuota " + installmentNumber + " de " + totalInstallments);

        return target;
    }
}
