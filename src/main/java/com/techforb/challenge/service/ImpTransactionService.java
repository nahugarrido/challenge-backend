package com.techforb.challenge.service;

import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.entity.Transaction;
import com.techforb.challenge.entity.Transfer;
import com.techforb.challenge.enums.TransactionState;
import com.techforb.challenge.enums.TransactionType;
import com.techforb.challenge.exception.GenericException;
import com.techforb.challenge.exception.NotAvailableBalance;
import com.techforb.challenge.repository.TransactionRepository;
import com.techforb.challenge.entity.User;
import com.techforb.challenge.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImpTransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ImpTransactionService(TransactionRepository transactionDetailRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionDetailRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void deposit(BigDecimal amount, String userID) {
        Optional<User> userOptional = userRepository.findByUserID(userID);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();
            user.setBalance(user.getBalance().add(amount));
            userRepository.save(user);

            Transaction transaction = Transaction.builder()
                    .type(TransactionType.DEPOSIT)
                    .state(TransactionState.COMPLETED)
                    .date(LocalDateTime.now())
                    .amount(amount)
                    .user(user)
                    .build();

            transactionRepository.save(transaction);
        }
    }

    @Override
    @Transactional
    public void withdraw(BigDecimal amount, String userID) {
        Optional<User> userOptional = userRepository.findByUserID(userID);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();
            if((user.getBalance().subtract(amount)).compareTo(BigDecimal.valueOf(0)) < 0 ) {
                throw new NotAvailableBalance(HttpStatus.FORBIDDEN, user.getBalance());
            } else {
                user.setBalance(user.getBalance().subtract(amount));
                userRepository.save(user);

                Transaction transaction = Transaction.builder()
                        .type(TransactionType.WITHDRAW)
                        .state(TransactionState.COMPLETED)
                        .date(LocalDateTime.now())
                        .amount(amount)
                        .user(user)
                        .build();

                transactionRepository.save(transaction);
            }
        }
    }

    @Override
    @Transactional
    public void transfer(BigDecimal amount, String userSender, String userDestinatary) {
        Optional<User> userSenderOptional = userRepository.findByUserID(userSender);
        Optional<User> userDestinataryOptional = userRepository.findByUserID(userDestinatary);

        if(userSenderOptional.isEmpty() || userDestinataryOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User auxSender = userSenderOptional.get();
            User auxDestinatary = userDestinataryOptional.get();

            if((auxSender.getBalance().subtract(amount).compareTo(BigDecimal.valueOf(0)) < 0)) {
                throw new NotAvailableBalance(HttpStatus.FORBIDDEN, auxSender.getBalance());
            } else {

                /// update user
                auxSender.setBalance(auxSender.getBalance().subtract(amount));
                userRepository.save(auxSender);

                /// save transaction
                Transfer transfer = new Transfer();
                transfer.setAmount(amount);
                transfer.setDate(LocalDateTime.now());
                transfer.setState(TransactionState.PENDING);
                transfer.setUserDestinatary(auxDestinatary);
                transfer.setUser(auxSender);
                transfer.setUserSender(auxSender);
                transfer.setType(TransactionType.TRANSFER);
                transactionRepository.save(transfer);

            }
        }
    }

    @Override
    @Transactional
    public void cancelTransfer(Long transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if(optionalTransaction.isEmpty()) {
            throw new GenericException("Transaction not found.", HttpStatus.NOT_FOUND);
        } else {
            Transaction transaction = optionalTransaction.get();
            if(transaction.getType() != TransactionType.TRANSFER) {
                throw new GenericException("Transaction is not a transfer.", HttpStatus.NOT_FOUND);
            }

            if(transaction.getState() == TransactionState.COMPLETED) {
                throw new GenericException("Transaction can not be cancel.", HttpStatus.FORBIDDEN);
            }

            if(transaction.getState() == TransactionState.CANCELED) {
                throw new GenericException("Transaction is already canceled.", HttpStatus.FORBIDDEN);
            }

            /// update transfer
            Transfer transfer = (Transfer) transaction;
            transfer.setState(TransactionState.CANCELED);
            transactionRepository.save(transfer);

            /// update user balance
            User userSender = transfer.getUserSender();
            BigDecimal amount = transfer.getAmount();
            userSender.setBalance(userSender.getBalance().add(amount));
            userRepository.save(userSender);
        }
    }

    public void completeTransfer(Long transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if(optionalTransaction.isEmpty()) {
            throw new GenericException("Transaction not found.", HttpStatus.NOT_FOUND);
        } else {
            Transaction transaction = optionalTransaction.get();
            if(transaction.getType() != TransactionType.TRANSFER) {
                throw new GenericException("Transaction is not a transfer.", HttpStatus.NOT_FOUND);
            }

            if(transaction.getState() == TransactionState.CANCELED) {
                throw new GenericException("Transaction is canceled.", HttpStatus.FORBIDDEN);
            }

            if(transaction.getState() == TransactionState.COMPLETED) {
                throw new GenericException("Transaction is already completed.", HttpStatus.FORBIDDEN);
            }

            /// update transfer
            Transfer transfer = (Transfer) transaction;
            transfer.setState(TransactionState.COMPLETED);
            transactionRepository.save(transfer);

            /// update user balance
            User userDestinatary = transfer.getUserDestinatary();
            BigDecimal amount = transfer.getAmount();
            userDestinatary.setBalance(userDestinatary.getBalance().add(amount));
            userRepository.save(userDestinatary);
        }
    }

    @Override
    public List<TransactionDTO> findAllTransferByUserID(String userID) {
        Optional<User> userOptional = userRepository.findByUserID(userID);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        List<Transaction> transactionList = transactionRepository.findAllByUser(user);
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        for(Transaction item : transactionList) {
            TransactionDTO aux = modelMapper.map(item, TransactionDTO.class);
            if(aux.getTransactionType() == TransactionType.TRANSFER) {
                /// transfer logic
            }
            transactionDTOList.add(aux);
        }
        return transactionDTOList;
    }
}
