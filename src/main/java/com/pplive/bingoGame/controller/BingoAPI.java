package com.pplive.bingoGame.controller;

import com.pplive.bingoGame.dto.BetRequest;
import com.pplive.bingoGame.dto.BetResponse;
import com.pplive.bingoGame.dto.BingoGame;
import com.pplive.bingoGame.service.BingoGameService;
import com.pplive.bingoGame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class BingoAPI{

    @Autowired
    BingoGameService bingoGameService;
    @Autowired
    UserService userService;

    @PostMapping("/game")
    public ResponseEntity<BingoGame> createGame(){
        BingoGame bingoGame = bingoGameService.createBingoGame();
        return new ResponseEntity<>(bingoGame, HttpStatus.CREATED);
    }

    @PostMapping("/bet")
    public ResponseEntity<BetResponse> placeBet(@RequestBody BetRequest requestBody) {
        BetResponse response = new BetResponse() ;
        if(bingoGameService.isBetOpen(Instant.now())){
        if(userService.checkBalance(requestBody.getBetAmount(),requestBody.getUserId())){
            response.setBetValid(true);
            response.setBetId(bingoGameService.generateBetId(requestBody.getUserId(),requestBody.getBetCode()));
            response.setGameId(requestBody.getGameId());
            response.setUserId(requestBody.getUserId());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;

        }}
    }


}
