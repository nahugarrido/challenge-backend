package com.techforb.challenge.controller;

import com.techforb.challenge.dto.UserDTO;
import com.techforb.challenge.service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/user")
public class UserController {
    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping(value = "/{userID}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable String userID) {
        return ResponseEntity.ok().body(iUserService.getUserInfo(userID));
    }


    @GetMapping(value = "/balance/{id}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok().body(iUserService.getBalance(id));
    }


}
