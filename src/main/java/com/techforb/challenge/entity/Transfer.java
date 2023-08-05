package com.techforb.challenge.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("Transfer")
public class Transfer extends Transaction {
    @ManyToOne
    @JoinColumn(name = "user_id_destinatary")
    private User userDestinatary;

}
