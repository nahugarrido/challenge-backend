package com.techforb.challenge.service;


import com.techforb.challenge.dto.UserDTO;
import com.techforb.challenge.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface IUserService {

    BigDecimal getBalance(Long id);

    UserDTO getUserInfo(String userID);

    List<UserDTO> findAllUsers();
}
