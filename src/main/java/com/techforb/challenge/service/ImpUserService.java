package com.techforb.challenge.service;

import com.techforb.challenge.dto.UserDTO;
import com.techforb.challenge.exception.GenericException;
import com.techforb.challenge.entity.User;
import com.techforb.challenge.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ImpUserService implements IUserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public ImpUserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

    @Override
    public UserDTO getUserInfo(String userID) {
        Optional<User> userOptional = userRepository.findByUserID(userID);
        if(userOptional.isEmpty()) {
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        return modelMapper.map(user, UserDTO.class);
    }

}
