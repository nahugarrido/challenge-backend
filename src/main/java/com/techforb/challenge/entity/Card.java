package com.techforb.challenge.entity;

import com.techforb.challenge.enums.CardType;
import jakarta.persistence.*;
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
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "card_expiration_date")
    private LocalDate expirationDate;

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private String cvv;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
