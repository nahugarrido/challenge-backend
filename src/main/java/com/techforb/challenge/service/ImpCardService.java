package com.techforb.challenge.service;

import com.techforb.challenge.dto.CardDTO;
import com.techforb.challenge.dto.CardSaveDTO;
import com.techforb.challenge.entity.Card;
import com.techforb.challenge.entity.User;
import com.techforb.challenge.enums.CardType;
import com.techforb.challenge.exception.GenericException;
import com.techforb.challenge.repository.CardRepository;
import com.techforb.challenge.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ImpCardService implements ICardService{
    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ImpCardService(CardRepository cardRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }


    @Override
    public void generateCard(CardSaveDTO cardSaveDTO) {
        Optional<User> userOptional = userRepository.findByUserID(cardSaveDTO.getUserID());
        if(userOptional.isEmpty()){
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();

            /// Verify that the user don't have an active debit card
            if(cardSaveDTO.getCardType() == CardType.DEBIT) {
                for(Card item : user.getCards()) {
                    if(item.getCardType() == CardType.DEBIT && item.getExpirationDate().isAfter(LocalDate.now())) {
                        throw new GenericException("The user already has a debit card.", HttpStatus.FORBIDDEN);
                    }
                }
            }

            Card card = Card.builder()
                    .cardType(cardSaveDTO.getCardType())
                    .cardNumber(generateRandomCardNumber())
                    .cardHolderName(user.getFirstname() + " " + user.getLastname())
                    .cvv(generateRandomCVV())
                    .expirationDate(cardSaveDTO.getDate().plusYears(2))
                    .user(user)
                    .build();

            cardRepository.save(card);
        }
    }

    @Override
    public List<CardDTO> findAllCardsByUserID(String userID) {
        Optional<User> userOptional = userRepository.findByUserID(userID);
        if(userOptional.isEmpty()){
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        } else {
            User user = userOptional.get();
            List<Card> cardList = user.getCards();
            List<CardDTO> cardDTOList = new ArrayList<>();

            for(Card item : cardList) {
                if(item.getExpirationDate().isAfter(LocalDate.now())) {
                    CardDTO aux = modelMapper.map(item, CardDTO.class);
                    cardDTOList.add(aux);
                }
            }

            return cardDTOList;
        }
    }

    @Override
    public CardDTO findDebitCardByUserID(String userID) {
        Optional<User> userOptional = userRepository.findByUserID(userID);
        if(userOptional.isEmpty()){
            throw new GenericException("User not found.", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        List<Card> cardList = user.getCards();

        for(Card item : cardList) {
            if( item.getCardType() == CardType.DEBIT && item.getExpirationDate().isAfter(LocalDate.now())) {
                return modelMapper.map(item, CardDTO.class);
            }
        }

        throw new GenericException("User dont have a valid debit card.", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<CardDTO> findAllCards() {
        List<Card> cardList = cardRepository.findAll();

        List<CardDTO> cardDTOList = new ArrayList<>();

        for(Card item : cardList) {
            CardDTO aux = modelMapper.map(item, CardDTO.class);
            cardDTOList.add(aux);
        }

        return cardDTOList;
    }

    private String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();

        // Assuming a card number has 16 digits
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

    private String generateRandomCVV() {
        Random random = new Random();
        return String.valueOf(random.nextInt(999 - 100 + 1) + 100);
    }
}
