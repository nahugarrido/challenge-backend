package com.techforb.challenge.entity;

import com.techforb.challenge.enums.InstallmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "number", nullable = false)
    private int number;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InstallmentStatus status;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @OneToOne(mappedBy = "installment")
    private Payment payment;

}