package com.techforb.challenge.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("transfer")
public class Transfer extends Transaction {

    @ManyToOne
    @JoinColumn(name = "user_id_destinatary")
    private User userDestinatary;

    @ManyToOne
    @JoinColumn(name = "user_id_sender")
    private User userSender;
}
