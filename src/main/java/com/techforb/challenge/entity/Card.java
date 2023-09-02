package com.techforb.challenge.entity;

import com.techforb.challenge.enums.CardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @NotNull
    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @NotNull
    @Column(name = "card_expiration_date", nullable = false)
    private LocalDate expirationDate;

    @NotNull
    @Column(name = "card_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotNull
    @Column(name = "cvv", nullable = false)
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
