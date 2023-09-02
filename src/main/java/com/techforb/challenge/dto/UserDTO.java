package com.techforb.challenge.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserDTO {
    @NotNull
    private Long id;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String userID;

    @NotEmpty
    private String picture;

    @NotEmpty
    private String email;

    @NotNull
    private BigDecimal balance;
}
