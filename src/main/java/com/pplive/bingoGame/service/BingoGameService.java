package com.pplive.bingoGame.service;

import com.pplive.bingoGame.dto.BingoGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class BingoGameService {
    @Autowired
    BingoGame bingoGame;

    public String createGameId(){
        String gameId = "BINGO"+ LocalDateTime.now();
        return gameId;
    }

    public List<Integer> generateTicket(){
        Random random = new Random();
        List<Integer> ticket = new ArrayList<>();
        Set<Integer> uniqueNumber = new HashSet<>();
        while (uniqueNumber.size()<3){
            int num = random.nextInt(10)+1;
            uniqueNumber.add(num);
        }
        ticket.addAll(uniqueNumber);
        uniqueNumber.clear();
        while (uniqueNumber.size()<3){
            int num = random.nextInt(10)+11;
            uniqueNumber.add(num);
        }
        ticket.addAll(uniqueNumber);
        uniqueNumber.clear();
        while (uniqueNumber.size()<3){
            int num = random.nextInt(10)+21;
            uniqueNumber.add(num);
        }
        ticket.addAll(uniqueNumber);
        uniqueNumber.clear();
        return ticket;
    }

    public BingoGame createBingoGame(){
        BingoGame bingoGame = new BingoGame();
        bingoGame.setGameId(createGameId());
        bingoGame.setGameStatus(true);
        bingoGame.setBetsOpen(true);
        bingoGame.getGameStartTime(Instant.now());
        bingoGame.setTicket(generateTicket());
        return bingoGame;
    }

    public String generateBetId(int userId, String betCode){
        String betId = userId+betCode+LocalDateTime.now();
        return betId;
    }

//    public boolean isBetOpen(Instant now){
//        ifbingoGame.getGameStartTime();
//    }


}
