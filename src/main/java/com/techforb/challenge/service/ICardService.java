package com.techforb.challenge.service;

import com.techforb.challenge.dto.CardDTO;
import com.techforb.challenge.dto.CardSaveDTO;

import java.util.List;

public interface ICardService {
    void generateCard(CardSaveDTO cardSaveDTO);

    List<CardDTO> findAllCardsByUserID(String userID);

    CardDTO findDebitCardByUserID(String userID);
}
