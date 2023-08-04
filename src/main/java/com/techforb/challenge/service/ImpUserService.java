package com.techforb.challenge.service;

import com.techforb.challenge.exception.GenericException;
import com.techforb.challenge.entity.User;
import com.techforb.challenge.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ImpUserService implements IUserService{

    private final UserRepository userRepository;

    private final ITransactionService iTransactionDetailService;



    public ImpUserService(UserRepository userRepository, ITransactionService iTransactionDetailService) {
        this.userRepository = userRepository;
        this.iTransactionDetailService = iTransactionDetailService;
    }


    @Override
    public BigDecimal getBalance(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();
            return user.getBalance();
        }
    }

}
