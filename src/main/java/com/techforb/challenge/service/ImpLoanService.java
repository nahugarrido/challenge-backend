package com.techforb.challenge.service;

import com.techforb.challenge.dto.InstallmentDTO;
import com.techforb.challenge.dto.LoanSaveDTO;

import com.techforb.challenge.entity.*;
import com.techforb.challenge.enums.InstallmentStatus;
import com.techforb.challenge.enums.TransactionStatus;
import com.techforb.challenge.enums.TransactionType;
import com.techforb.challenge.exception.GenericException;
import com.techforb.challenge.exception.NotAvailableBalance;
import com.techforb.challenge.repository.InstallmentRepository;
import com.techforb.challenge.repository.LoanRepository;
import com.techforb.challenge.repository.TransactionRepository;
import com.techforb.challenge.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ImpLoanService implements ILoanService {
    private final LoanRepository loanRepository;
    private final InstallmentRepository installmentRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ImpLoanService(LoanRepository loanRepository, InstallmentRepository installmentRepository, TransactionRepository transactionRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.installmentRepository = installmentRepository;
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void generateLoan(LoanSaveDTO loanSaveDTO) {
        Optional<User> userOptional = userRepository.findByUserID(loanSaveDTO.getUserID());
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();

            Loan loan = Loan.builder()
                    .totalInstallments(loanSaveDTO.getTotalInstallments())
                    .user(user)
                    .paidInstallments(0)
                    .interestRate(loanSaveDTO.getInterestRate())
                    .amount(loanSaveDTO.getAmount())
                    .date(loanSaveDTO.getDate())
                    .build();

            loanRepository.save(loan);
            generateLoanInstallments(loan.getId());

            user.setBalance(user.getBalance().add(loan.getAmount()));
            userRepository.save(user);
       }
    }

    @Override
    @Transactional
    public void payInstallment(Long userId, Long installmentId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Installment> installmentOptional = installmentRepository.findById(installmentId);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        if(installmentOptional.isEmpty()) {
            throw new GenericException("Installment not found.", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        Installment installment = installmentOptional.get();
        BigDecimal amount = installment.getAmount();

        if(user.getBalance().compareTo(amount) < 0) {
            throw new NotAvailableBalance(HttpStatus.FORBIDDEN,user.getBalance());
        } else {

            /// update user balance
         user.setBalance(user.getBalance().subtract(amount));
         userRepository.save(user);

         /// update installment status
         installment.setStatus(InstallmentStatus.PAID);
         installmentRepository.save(installment);

         /// update loan count
         Loan loan =  installment.getLoan();
         loan.setPaidInstallments(loan.getPaidInstallments() + 1);
         loanRepository.save(loan);

         /// generate transaction record
            Payment payment = new Payment();
                   payment.setAmount(amount);
                    payment.setDate(LocalDateTime.now());
                    payment.setType(TransactionType.PAYMENT);
                    payment.setStatus(TransactionStatus.COMPLETED);
                    payment.setUser(user);
                    payment.setInstallment(installment);
                    transactionRepository.save(payment);
        }
    }

    @Override
    public List<InstallmentDTO> findAllInstallmentById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();
            List<Loan> loansList = loanRepository.findAllByUser(user);
            List<InstallmentDTO> installmentDTOList = new ArrayList<>();

            LocalDate date = LocalDate.now();

            /// Filtering the installments
            for(Loan item : loansList) {
                List<Installment> listAux = item.getInstallments();
               for(Installment ins : listAux) {
                   /// filtering the ones that should not appear yet
                   if(ins.getIssueDate().isAfter(date)) {
                       break;
                       /// adding the ones that should appear
                   } else if(ins.getStatus() == InstallmentStatus.UNPAID) {
                       InstallmentDTO aux = modelMapper.map(ins, InstallmentDTO.class);
                       installmentDTOList.add(aux);
                       /// adding last payment in case of payed loan
                   } else if(ins.getNumber() == item.getTotalInstallments() && ins.getStatus() == InstallmentStatus.PAID) {
                       /// verification to fix case when the user payed his last installment but not the previous ones
                       if(item.getTotalInstallments() == item.getPaidInstallments()) {
                           InstallmentDTO aux = modelMapper.map(ins, InstallmentDTO.class);
                           installmentDTOList.add(aux);
                       }
                   }
               }
            }

            return installmentDTOList;
        }
    }

    private void generateLoanInstallments(Long id) {
        Optional<Loan> loanOptional = loanRepository.findById(id);
        if(loanOptional.isEmpty()) {
            throw new GenericException("Loan not found.", HttpStatus.NOT_FOUND);
        } else {
            Loan loan = loanOptional.get();

            List<Installment> newInstallments = new ArrayList<>();
            LocalDate loanGenerationDate = loan.getDate();

            // Generate first date of installment
            LocalDate firstInstallmentIssueDate = loanGenerationDate.plusMonths(1).withDayOfMonth(1);

            // Generate all the installments
            for (int i = 1; i <= loan.getTotalInstallments(); i++) {
                LocalDate installmentIssueDate = firstInstallmentIssueDate.plusMonths(i).withDayOfMonth(1);
                LocalDate installmentDueDate = firstInstallmentIssueDate.plusMonths(i).withDayOfMonth(10);

                Installment installment = new Installment();
                installment.setLoan(loan);
                installment.setNumber(i);
                installment.setIssueDate(installmentIssueDate);
                installment.setDueDate(installmentDueDate);
                installment.setStatus(InstallmentStatus.UNPAID);

                // Calculating installment amount
                double monthInterestRate = loan.getInterestRate() / 12;
                BigDecimal dividing = loan.getAmount().multiply(BigDecimal.valueOf(monthInterestRate));
                BigDecimal divisor = BigDecimal.ONE.subtract(BigDecimal.valueOf(Math.pow(1 + monthInterestRate, -loan.getTotalInstallments())));
                BigDecimal installmentAmount = dividing.divide(divisor, 2, RoundingMode.HALF_UP);
                installment.setAmount(installmentAmount);

                newInstallments.add(installment);
            }

            installmentRepository.saveAll(newInstallments);
        }
    }

}

