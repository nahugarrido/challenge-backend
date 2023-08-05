package com.techforb.challenge.service;

import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.entity.Transaction;
import com.techforb.challenge.entity.Transfer;
import com.techforb.challenge.enums.MovementType;
import com.techforb.challenge.enums.TransactionStatus;
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
                    .status(TransactionStatus.COMPLETED)
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
                        .status(TransactionStatus.COMPLETED)
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
                transfer.setStatus(TransactionStatus.PENDING);
                transfer.setUserDestinatary(auxDestinatary);
                transfer.setUser(auxSender);
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

            if(transaction.getStatus() == TransactionStatus.COMPLETED) {
                throw new GenericException("Transaction can not be cancel.", HttpStatus.FORBIDDEN);
            }

            if(transaction.getStatus() == TransactionStatus.CANCELED) {
                throw new GenericException("Transaction is already canceled.", HttpStatus.FORBIDDEN);
            }

            /// update transfer
            Transfer transfer = (Transfer) transaction;
            transfer.setStatus(TransactionStatus.CANCELED);
            transactionRepository.save(transfer);

            /// update user balance
            User userSender = transfer.getUser();
            BigDecimal amount = transfer.getAmount();
            userSender.setBalance(userSender.getBalance().add(amount));
            userRepository.save(userSender);
        }
    }

    @Override
    @Transactional
    public void completeTransfer(Long transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if(optionalTransaction.isEmpty()) {
            throw new GenericException("Transaction not found.", HttpStatus.NOT_FOUND);
        } else {
            Transaction transaction = optionalTransaction.get();
            if(transaction.getType() != TransactionType.TRANSFER) {
                throw new GenericException("Transaction is not a transfer.", HttpStatus.NOT_FOUND);
            }

            if(transaction.getStatus() == TransactionStatus.CANCELED) {
                throw new GenericException("Transaction is canceled.", HttpStatus.FORBIDDEN);
            }

            if(transaction.getStatus() == TransactionStatus.COMPLETED) {
                throw new GenericException("Transaction is already completed.", HttpStatus.FORBIDDEN);
            }

            /// update transfer
            Transfer transfer = (Transfer) transaction;
            transfer.setStatus(TransactionStatus.COMPLETED);
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

            /// In case of transfer
            if(aux.getTransactionType() == TransactionType.TRANSFER) {
                Long id = user.getId();
                Transfer transfer = (Transfer) item;
                /// if the user is the sender
                if(id.equals(transfer.getUser().getId())) {
                    String fullName = transfer.getUserDestinatary().getFirstname() +
                            " " +
                            transfer.getUserDestinatary().getLastname();

                    aux.setHeader(fullName);
                    aux.setMovementType(MovementType.EXPENSE);
                    /// if the user is the destinatary
                } else if(id.equals(transfer.getUserDestinatary().getId())) {
                    String fullName = transfer.getUser().getFirstname() +
                            " " +
                            transfer.getUser().getLastname();

                    aux.setHeader(fullName);
                    aux.setMovementType(MovementType.INCOME);
                }
            }

            transactionDTOList.add(aux);
        }
        return transactionDTOList;
    }
}
