package com.techforb.challenge.controller;

import com.techforb.challenge.dto.ApiResponse;
import com.techforb.challenge.dto.InstallmentDTO;
import com.techforb.challenge.dto.LoanSaveDTO;
import com.techforb.challenge.service.ILoanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/loans")
public class LoanController {
    private final ILoanService iLoanService;

    public LoanController(ILoanService iLoanService) {
        this.iLoanService = iLoanService;
    }
    @PostMapping(value = "/generate")
    public ResponseEntity<ApiResponse> generateLoan(@Valid @RequestBody LoanSaveDTO loanSaveDTO) {
        iLoanService.generateLoan(loanSaveDTO);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/{userId}/pay/{installmentId}")
    public ResponseEntity<ApiResponse> payInstallment(@PathVariable Long userId, @PathVariable Long installmentId) {
        iLoanService.payInstallment(userId, installmentId);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<InstallmentDTO>> findAllInstallmentById(@PathVariable Long userId) {
        return ResponseEntity.ok().body(iLoanService.findAllInstallmentById(userId));
    }


}
