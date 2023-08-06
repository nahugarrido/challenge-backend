package com.techforb.challenge.controller;

import com.techforb.challenge.dto.CardDTO;
import com.techforb.challenge.dto.CardSaveDTO;
import com.techforb.challenge.dto.LoanSaveDTO;
import com.techforb.challenge.dto.UserDTO;
import com.techforb.challenge.service.ICardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {
    private final ICardService iCardService;

    public CardController(ICardService iCardService) {
        this.iCardService = iCardService;
    }

    @PostMapping(value = "/generate")
    public ResponseEntity<String> generateCard(@RequestBody CardSaveDTO cardSaveDTO) {
        iCardService.generateCard(cardSaveDTO);
        return ResponseEntity.ok().body("Successful operation.");
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
