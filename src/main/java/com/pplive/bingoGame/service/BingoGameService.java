package com.pplive.bingoGame.service;

import com.pplive.bingoGame.dto.BingoGame;
import com.pplive.bingoGame.repository.BingoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.lang.*;

@Service
public class BingoGameService {

    @Autowired
    BingoGame bingoGame;


    HashMap<String, Integer> betMap = new HashMap<String, Integer>(){{
        put("r1", 2101);
        put("r2",2102);
        put("r3",2103);
        put("c1",2104);
        put("c2",2105);
        put("c3",2106);
    }};

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
        bingoGame = new BingoGame();
        bingoGame.setGameId(createGameId());
        bingoGame.setGameStatus(true);
        bingoGame.setBetsOpen(true);
        bingoGame.setGameStartTime(Instant.now());
        bingoGame.setTicket(generateTicket());
        bingoGame.setNumberSequence(new ArrayList<>());
        return bingoGame;
    }

    public String generateBetId(int userId, String betCode){
        System.out.println(LocalDateTime.now());
        String betId = userId+betCode+LocalDateTime.now();
        System.out.println(betId);
        return betId;
    }

    public int betCodeMapping(String betCode){
        return betMap.get(betCode);
    }

    public boolean compareGameId(String cancelGameId, String currentGameId, boolean currentGameStatus){
        if(currentGameStatus && (cancelGameId.equalsIgnoreCase(currentGameId))){
            return true;
        }
        else{
            return false;
        }
    }

}
