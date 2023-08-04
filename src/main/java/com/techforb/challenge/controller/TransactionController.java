package com.techforb.challenge.controller;

import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.dto.TransferSaveDTO;
import com.techforb.challenge.service.ITransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final ITransactionService iTransactionService;

    public TransactionController(ITransactionService iTransactionDetailService) {
        this.iTransactionService = iTransactionDetailService;
    }

    @PostMapping(value = "/deposit/{userID}")
    public ResponseEntity<String> deposit(@RequestBody BigDecimal amount, @PathVariable String userID) {
        iTransactionService.deposit(amount, userID);
        return ResponseEntity.ok().body("Successful operation.");
    }

    @PostMapping(value = "/withdraw/{userID}")
    public ResponseEntity<String> withdraw(@RequestBody BigDecimal amount, @PathVariable String userID) {
        iTransactionService.withdraw(amount, userID);
        return ResponseEntity.ok().body("Successful operation.");
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferSaveDTO transferSaveDTO) {
        iTransactionService.transfer(transferSaveDTO.getAmount(), transferSaveDTO.getUserSender(), transferSaveDTO.getUserDestinatary());
        return ResponseEntity.ok().body("Successful operation.");
    }

    @GetMapping(value = "/{userID}")
    public ResponseEntity<List<TransactionDTO>> findAllTransactionsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok().body(iTransactionService.findAllTransferByUserID(userID));
    }
}
