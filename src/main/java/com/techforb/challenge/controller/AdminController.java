package com.techforb.challenge.controller;

import com.techforb.challenge.dto.*;
import com.techforb.challenge.service.ICardService;
import com.techforb.challenge.service.ILoanService;
import com.techforb.challenge.service.ITransactionService;
import com.techforb.challenge.service.IUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final ITransactionService iTransactionService;
    private final ILoanService iLoanService;
    private final ICardService iCardService;
    private final IUserService iUserService;


    public AdminController(ITransactionService iTransactionService, ILoanService iLoanService, ICardService iCardService, IUserService iUserService) {
        this.iTransactionService = iTransactionService;
        this.iLoanService = iLoanService;
        this.iCardService = iCardService;
        this.iUserService = iUserService;
    }

    ///@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cancel/{transferId}")
    public ResponseEntity<ApiResponse> cancelTransfer(@PathVariable Long transferId) {
        iTransactionService.cancelTransfer(transferId);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    ///@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/complete/{transferId}")
    public ResponseEntity<ApiResponse> completeTransfer(@PathVariable Long transferId) {
        iTransactionService.completeTransfer(transferId);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    ///@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all/transactions")
    public ResponseEntity<List<TransactionDTO>> findAllTransactions() {
        return ResponseEntity.ok().body(iTransactionService.findAllTransactions());
    }

    ///@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all/loans")
    public ResponseEntity<List<InstallmentDTO>> findAllInstalments() {
        return ResponseEntity.ok().body(iLoanService.findAllInstallments());
    }

    ///@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all/cards")
    public ResponseEntity<List<CardDTO>> findAllCards() {
        return ResponseEntity.ok().body(iCardService.findAllCards());
    }

    ///@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all/users")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok().body(iUserService.findAllUsers());
    }
}
