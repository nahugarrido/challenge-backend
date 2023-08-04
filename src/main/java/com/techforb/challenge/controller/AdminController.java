package com.techforb.challenge.controller;

import com.techforb.challenge.service.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ITransactionService iTransactionDetailService;

    public AdminController(ITransactionService iTransactionDetailService) {
        this.iTransactionDetailService = iTransactionDetailService;
    }


    @PostMapping(value = "/cancel/{transferId}")
    public ResponseEntity<String> cancelTransfer(@PathVariable Long transferId) {
        iTransactionDetailService.cancelTransfer(transferId);
        return ResponseEntity.ok().body("Successful operation");
    }

    @PostMapping(value = "/complete/{transferId}")
    public ResponseEntity<String> completeTransfer(@PathVariable Long transferId) {
        iTransactionDetailService.completeTransfer(transferId);
        return ResponseEntity.ok().body("Successful operation");
    }
}
