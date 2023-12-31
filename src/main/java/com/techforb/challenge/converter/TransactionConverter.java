package com.techforb.challenge.converter;

import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.entity.Transaction;
import com.techforb.challenge.enums.MovementType;
import com.techforb.challenge.enums.TransactionType;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter extends AbstractConverter<Transaction, TransactionDTO> {

    @Override
    protected TransactionDTO convert(Transaction source) {
        TransactionDTO target = new TransactionDTO();

        // Realizar el mapeo de los campos comunes entre Transaction y TransactionDTO
        target.setDate(source.getDate());
        target.setTransactionType(source.getType());
        target.setAmount(source.getAmount());
        target.setStatus(source.getStatus());

        // Evaluar el tipo de transacción y aplicar lógicas personalizadas
        if (source.getType() == TransactionType.DEPOSIT) {
            target.setHeader("CASH");
            target.setMovementType(MovementType.INCOME);
        } else if (source.getType() == TransactionType.WITHDRAW) {
            target.setHeader("CASH");
            target.setMovementType(MovementType.EXPENSE);
        }

        return target;
    }
}