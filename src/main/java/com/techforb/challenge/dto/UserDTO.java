package com.techforb.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String firstname;

    private String lastname;

    private String userID;

    private String picture;

    private String email;

    private BigDecimal balance;
}
