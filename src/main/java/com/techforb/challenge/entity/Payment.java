package com.techforb.challenge.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("Payment")
public class Payment extends Transaction {
    @OneToOne
    @JoinColumn(name = "installment_id")
    private Installment installment;
}
