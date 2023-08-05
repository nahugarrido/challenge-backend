package com.techforb.challenge.service;

import com.techforb.challenge.dto.InstallmentDTO;
import com.techforb.challenge.dto.LoanSaveDTO;

import java.util.List;


public interface ILoanService {
    void generateLoan(LoanSaveDTO loanSaveDTO);

    void payInstallment(Long userId, Long installmentId);

    List<InstallmentDTO> findAllInstallmentById(Long userId);
}
