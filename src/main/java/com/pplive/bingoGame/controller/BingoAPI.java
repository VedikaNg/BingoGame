package com.pplive.bingoGame.controller;

import com.pplive.bingoGame.constant.Constants;
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
import java.time.LocalDateTime;

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
    public ResponseEntity<?> createGame(){
        System.out.println(bingoGame.getGameStatus());
        if(!bingoGame.getGameStatus()){
            bingoGame = bingoGameService.createBingoGame();
            return new ResponseEntity<>(bingoGame, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(new SendMessage(Constants.INVALIDE_GAME_CREATION),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/bet")
    public ResponseEntity<?> placeBet(@RequestBody BetRequest requestBody) {
        BetResponse response = new BetResponse() ;
        if(!userService.isBetAlreadyPlaced(requestBody.getUserId(), requestBody.getGameId()) &&
                bingoGame.getBetsOpen(Instant.now()) &&
                (bingoGame.getGameId().equalsIgnoreCase(requestBody.getGameId()))){
            if(userService.checkBalance(requestBody.getBetAmount(),requestBody.getUserId(),
                    requestBody.getBetCode(), requestBody.getGameId())){
                response.setBetValid(true);
                bingoDB.insertIntoTransactionTable(requestBody.getUserId(), requestBody.getGameId(),
                        "BET", requestBody.getBetAmount(), LocalDateTime.now() ,"SUCCESS");
                response.setBetId(bingoGameService.generateBetId(requestBody.getUserId(),requestBody.getBetCode()));
                response.setGameId(requestBody.getGameId());
                response.setUserId(requestBody.getUserId());
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            }
            else{
                bingoDB.insertIntoTransactionTable(requestBody.getUserId(), requestBody.getGameId(),
                        "BET", requestBody.getBetAmount(), LocalDateTime.now(),"FAILED");
                return new ResponseEntity<>(new SendMessage(Constants.INSUFFICIENT_BALANCE),HttpStatus.BAD_REQUEST) ;
            }
        }
        else{
            bingoDB.insertIntoTransactionTable(requestBody.getUserId(), requestBody.getGameId(),
                    "BET", requestBody.getBetAmount(), LocalDateTime.now(),"FAILED");
            return new ResponseEntity<>(new SendMessage(Constants.BET_ALREADY_PLACED),HttpStatus.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping("/bingo/result")
    public ResponseEntity<?> result(@RequestBody ResultRequest resultRequest){
        if(resultRequest.getGameId().equalsIgnoreCase(bingoGame.getGameId()) && bingoGame.getGameStatus()){
            Boolean gameEnds = userService.checkNumberInTicket(resultRequest.getRandomNumber(),
                    bingoGame.getTicket(), bingoGame);
            if(gameEnds){
                bingoGame.setGameStatus(false);

                BetDetails betDetails = bingoDB.findUserIdBetAmountBetCodeByGameId(resultRequest.getGameId());
                bingoDB.updateBalanceInDB(bingoDB.findBalanceByUserId(
                        betDetails.getUserId())+bingoGame.getPayout(), betDetails.getUserId());
                bingoDB.insertIntoTransactionTable(betDetails.getUserId(),
                        bingoGame.getGameId(),"PAYOUT", bingoGame.getPayout(),
                        LocalDateTime.now() ,"SUCCESS");
                PayoutDetails payoutDetails = new PayoutDetails();
                payoutDetails.setPayout(bingoGame.getPayout());
                payoutDetails.setGameId(bingoGame.getGameId());
                return new ResponseEntity<>(payoutDetails, HttpStatus.ACCEPTED);
            }
            else{
                ResultResponse response = new ResultResponse();
                bingoGame.getNumberSequence().add(resultRequest.getRandomNumber());
                response.setNumberSequence(bingoGame.getNumberSequence());
                response.setGameId(resultRequest.getGameId());
                System.out.println(bingoGame.getNumberLeftToWin());
                response.setNumberLeftToWin(bingoGame.getNumberLeftToWin());
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(new SendMessage(Constants.INVALID_GAME), HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/cancelGame/{gameId}")
    public ResponseEntity<?> cancelGame(@PathVariable String gameId){
    System.out.println(bingoGameService.compareGameId(gameId, bingoGame.getGameId(), bingoGame.getGameStatus()));
        if(bingoGameService.compareGameId(gameId, bingoGame.getGameId(), bingoGame.getGameStatus())){
            BetDetails betDetails = bingoDB.findUserIdBetAmountBetCodeByGameId(gameId);
            bingoGame.setGameStatus(false);
            int balance = bingoDB.findBalanceByUserId(betDetails.getUserId());
            System.out.println(balance);
            bingoDB.updateBalanceInDB((balance+betDetails.getBetAmount()), betDetails.getUserId());
            bingoDB.insertIntoTransactionTable(betDetails.getUserId(), gameId,
                    "REFUND", betDetails.getBetAmount(), LocalDateTime.now() ,"SUCCESS");
            return new ResponseEntity<>(new SendMessage(Constants.GAME_CANCEL), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new SendMessage(Constants.INVALID_GAME),HttpStatus.BAD_REQUEST);
    }

}
