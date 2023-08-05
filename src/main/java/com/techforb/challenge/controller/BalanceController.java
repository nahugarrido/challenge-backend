package com.techforb.challenge.controller;

import com.techforb.challenge.service.IBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {
    private final IBalanceService iBalanceService;

    public BalanceController(IBalanceService iBalanceService) {
        this.iBalanceService = iBalanceService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        return ResponseEntity.ok().body(iBalanceService.getBalance(id));
    }


}
