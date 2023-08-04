package com.techforb.challenge.service;


import com.techforb.challenge.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ITransactionService {

    void deposit(BigDecimal amount, String userID);

    void withdraw(BigDecimal amount, String userID);

    void transfer(BigDecimal amount, String userSender, String userDestinatary);
    void cancelTransfer(Long transactionId);
    void completeTransfer(Long transactionId);

    List<TransactionDTO> findAllTransferByUserID(String userID);
}
