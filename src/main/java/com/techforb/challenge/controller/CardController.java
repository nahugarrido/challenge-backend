package com.techforb.challenge.controller;

import com.techforb.challenge.dto.ApiResponse;
import com.techforb.challenge.dto.CardDTO;
import com.techforb.challenge.dto.CardSaveDTO;
import com.techforb.challenge.service.ICardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/cards")
public class CardController {
    private final ICardService iCardService;

    public CardController(ICardService iCardService) {
        this.iCardService = iCardService;
    }

    @PostMapping(value = "/generate")
    public ResponseEntity<ApiResponse> generateCard(@RequestBody CardSaveDTO cardSaveDTO) {
        iCardService.generateCard(cardSaveDTO);
        ApiResponse response = new ApiResponse("Successful operation.");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/all/{userID}")
    public ResponseEntity<List<CardDTO>> findAllCardsByUserID(@PathVariable String userID) {
        return ResponseEntity.ok().body(iCardService.findAllCardsByUserID(userID));
    }

    @GetMapping(value = "/debit/{userID}")
    public ResponseEntity<CardDTO> findDebitCardByUserID(@PathVariable String userID) {
        return ResponseEntity.ok().body(iCardService.findDebitCardByUserID(userID));
    }
}
