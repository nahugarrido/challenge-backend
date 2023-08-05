package com.techforb.challenge.entity;

import com.techforb.challenge.enums.InstallmentStatus;
import com.techforb.challenge.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "installment")
public class Installment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "number")
    private int number;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @OneToOne(mappedBy = "installment")
    private Payment payment;

}