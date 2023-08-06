package com.techforb.challenge.converter;

import com.techforb.challenge.dto.InstallmentDTO;
import com.techforb.challenge.entity.Installment;
import com.techforb.challenge.entity.Loan;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class LoanConverter extends AbstractConverter<Installment, InstallmentDTO> {

    @Override
    protected InstallmentDTO convert(Installment source) {
        Loan loan = source.getLoan();
        InstallmentDTO target = new InstallmentDTO();
        target.setId(source.getId());
        target.setStatus(source.getStatus());
        target.setDate(source.getIssueDate());
        target.setAmount(source.getAmount());
        target.setInterestRate(loan.getInterestRate());
        target.setTotalAmount(loan.getAmount());
        int installmentNumber = source.getNumber();
        int totalInstallments = loan.getTotalInstallments();
        target.setNumber("Installment " + installmentNumber + " of " + totalInstallments);

        return target;
    }
}
