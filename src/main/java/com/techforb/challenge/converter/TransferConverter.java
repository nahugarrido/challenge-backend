package com.techforb.challenge.converter;

import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.entity.Transfer;
import com.techforb.challenge.enums.MovementType;
import com.techforb.challenge.enums.TransactionType;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class TransferConverter extends AbstractConverter<Transfer, TransactionDTO> {

    @Override
    protected TransactionDTO convert(Transfer source) {
        TransactionDTO target = new TransactionDTO();

        target.setDate(source.getDate());
        target.setTransactionType(source.getType());
        target.setAmount(source.getAmount());
        target.setStatus(source.getStatus());
        target.setHeader("Not assignet yet.");
        target.setTransactionType(TransactionType.TRANSFER);
        target.setMovementType(MovementType.EXPENSE);

        return target;
    }
}