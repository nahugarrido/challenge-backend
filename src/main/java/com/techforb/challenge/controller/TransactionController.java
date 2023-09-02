package com.techforb.challenge.controller;

import com.techforb.challenge.dto.ApiResponse;
import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.dto.TransferSaveDTO;
import com.techforb.challenge.service.ITransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final ITransactionService iTransactionService;

    public TransactionController(ITransactionService iTransactionDetailService) {
        this.iTransactionService = iTransactionDetailService;
    }

    @PostMapping(value = "/deposit/{userID}")
    public ResponseEntity<ApiResponse> deposit(@Valid @RequestBody BigDecimal amount, @PathVariable String userID) {
        iTransactionService.deposit(amount, userID);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/withdraw/{userID}")
    public ResponseEntity<ApiResponse> withdraw(@Valid @RequestBody BigDecimal amount, @PathVariable String userID) {
        iTransactionService.withdraw(amount, userID);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<ApiResponse> transfer(@Valid @RequestBody TransferSaveDTO transferSaveDTO) {
        iTransactionService.transfer(transferSaveDTO.getAmount(), transferSaveDTO.getUserSender(), transferSaveDTO.getUserDestinatary());
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{userID}")
    public ResponseEntity<List<TransactionDTO>> findAllTransactionsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok().body(iTransactionService.findAllTransferByUserID(userID));
    }
}
