package com.techforb.challenge.service;


import com.techforb.challenge.dto.UserDTO;

import java.math.BigDecimal;

public interface IUserService {

    BigDecimal getBalance(Long id);

    UserDTO getUserInfo(String userID);
}
