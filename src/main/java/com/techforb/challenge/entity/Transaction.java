package com.techforb.challenge.entity;

import com.techforb.challenge.enums.TransactionStatus;
import com.techforb.challenge.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
