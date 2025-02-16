package com.pplive.bingoGame.controller;

import com.pplive.bingoGame.dto.*;
import com.pplive.bingoGame.repository.BingoDB;
import com.pplive.bingoGame.service.BingoGameService;
import com.pplive.bingoGame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    BingoGame bingoGame;
    @Autowired
    BingoDB bingoDB;


    @PostMapping("/game")
    public ResponseEntity<BingoGame> createGame(){
        System.out.println(bingoGame.getGameStatus());
        if(!bingoGame.getGameStatus()){
            bingoGame = bingoGameService.createBingoGame();
            return new ResponseEntity<>(bingoGame, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/bet")
    public ResponseEntity<BetResponse> placeBet(@RequestBody BetRequest requestBody) {
        BetResponse response = new BetResponse() ;
        if(!userService.isBetAlreadyPlaced(requestBody.getUserId(), requestBody.getGameId()) && bingoGame.getBetsOpen(Instant.now()) && (bingoGame.getGameId().equalsIgnoreCase(requestBody.getGameId()))){
            if(userService.checkBalance(requestBody.getBetAmount(),requestBody.getUserId(),requestBody.getBetCode(), requestBody.getGameId())){
                response.setBetValid(true);
                response.setBetId(bingoGameService.generateBetId(requestBody.getUserId(),requestBody.getBetCode()));
                response.setGameId(requestBody.getGameId());
                response.setUserId(requestBody.getUserId());
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ;
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping("/bingo/result")
    public ResponseEntity<ResultResponse> result(@RequestBody ResultRequest resultRequest){
        userService.checkNumberInTicket(resultRequest.getRandomNumber(), bingoGame.getTicket());
        ResultResponse response = new ResultResponse();
        bingoGame.getNumberSequence().add(resultRequest.getRandomNumber());
        response.setNumberSequence(bingoGame.getNumberSequence());
        response.setGameId(resultRequest.getGameId());
        System.out.println("After calculation");
        System.out.println("Before setting");
        System.out.println(userService.numberLeft());
        response.setNumberLeftToWin(userService.numberLeft());
//        System.out.println("After setting");
//        System.out.println(bingoGame.getNumberLeftToWin());
        response.setPayout(userService.payout());
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/cancelGame/{gameId}")
    public void cancelGame(@PathVariable String gameId){
    System.out.println(bingoGameService.compareGameId(gameId, bingoGame.getGameId(), bingoGame.getGameStatus()));
        if(bingoGameService.compareGameId(gameId, bingoGame.getGameId(), bingoGame.getGameStatus())){
            BetDetails betDetails = bingoDB.findUserIdBetAmountBetCodeByGameId(gameId);
            int balance = bingoDB.findBalanceByUserId(betDetails.getUserId());
            System.out.println(balance);
            bingoDB.updateBalanceInDB((balance+betDetails.getBetAmount()), betDetails.getUserId());
        }
    }

}
