package com.pplive.bingoGame.service;

import com.pplive.bingoGame.dto.BetDetails;
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

    @Autowired
    BingoDB bingoDB;

    @Autowired
    UserService userService;
    

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


    public void checkNumberInTicket(int randomNumber, List<Integer> ticket) {
        if (randomNumber <= 10) {
            for (int i = 0; i <= 2; i++) {
                if (ticket.get(i) == randomNumber) {
                    ticket.set(i, -1);
                    List<Integer> leftInColumn = bingoGame.getNumberLeftInColumn();
                    int columnCount= leftInColumn.get(0);
                    leftInColumn.set(0, columnCount - 1);
                    bingoGame.setNumberLeftInColumn(leftInColumn);

                    if (columnCount - 1 == 1) {
                        for (int k = 0; k <= 2; k++) {
                            if (ticket.get(k) != -1) {
                                bingoGame.getNumberLeftToWin().add(ticket.get(k));
                            }
                        }
                    }
                    List<Integer> leftInRow = bingoGame.getNumberLeftInRow();
                    int rowCount = leftInRow.get(i);
                    leftInRow.set(i, rowCount - 1);
                    bingoGame.setNumberLeftInRow(leftInRow);

                    if (rowCount - 1 == 1) {
                        if(i%3==0){
                            for(int k=0;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                        else if(i%3==1){
                            for(int k=1;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                        else{
                            for(int k=2;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                    }

                    // Update game status
                    if (columnCount - 1 == 0 || rowCount - 1 == 0) {
                        bingoGame.setGameStatus(false);
                    }
                    break;
                }
            }
        }
        else if (randomNumber <= 20) {
            for (int i = 3; i <= 5; i++) {
                if (ticket.get(i) == randomNumber) {
                    ticket.set(i, -1);
                    List<Integer> leftInColumn = bingoGame.getNumberLeftInColumn();
                    int columnCount = leftInColumn.get(1);
                    leftInColumn.set(1, columnCount - 1);
                    bingoGame.setNumberLeftInColumn(leftInColumn);

                    if (columnCount - 1 == 1) {
                        for (int k = 3; k <= 5; k++) {
                            if (ticket.get(k) != -1) {
                                bingoGame.getNumberLeftToWin().add(ticket.get(k));
                            }
                        }
                    }
                    List<Integer> leftInRow = bingoGame.getNumberLeftInRow();
                    int rowCount = leftInRow.get(i - 3);
                    leftInRow.set(i - 3, rowCount - 1);
                    bingoGame.setNumberLeftInRow(leftInRow);

                    if (rowCount - 1 == 1) {
                        if(i%3==0){
                            for(int k=0;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                        else if(i%3==1){
                            for(int k=1;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                        else{
                            for(int k=2;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                    }

                    if (columnCount - 1 == 0 || rowCount - 1 == 0) {
                        bingoGame.setGameStatus(false);
                    }
                    break;
                }
            }
        }
        else {
            for (int i = 6; i <= 8; i++) {
                if (ticket.get(i) == randomNumber) {
                    ticket.set(i, -1);
                    List<Integer> leftInColumn = bingoGame.getNumberLeftInColumn();
                    int columnCount = leftInColumn.get(2);
                    leftInColumn.set(2, columnCount - 1);
                    bingoGame.setNumberLeftInColumn(leftInColumn);

                    if (columnCount - 1 == 1) {
                        for (int k = 6; k <= 8; k++) {
                            if (ticket.get(k) != -1) {
                                bingoGame.getNumberLeftToWin().add(ticket.get(k));
                            }
                        }
                    }

                    List<Integer> leftInRow = bingoGame.getNumberLeftInRow();
                    int rowCount = leftInRow.get(i - 6);
                    leftInRow.set(i - 6, rowCount - 1);
                    bingoGame.setNumberLeftInRow(leftInRow);

                    if (rowCount - 1 == 1) {
                        if(i%3==0){
                            for(int k=0;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                        else if(i%3==1){
                            for(int k=1;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                        else{
                            for(int k=2;k<9;k+=3){
                                if(ticket.get(k)!=-1){
                                    bingoGame.getNumberLeftToWin().add(ticket.get(k));
                                }
                            }
                        }
                    }

                    if (columnCount - 1 == 0 || rowCount - 1 == 0) {
                        bingoGame.setGameStatus(false);
                        // pass the userId to both the function

//                        if(checkIfUserWins(,bingoGame.getTicket()))
//                        {
//                            calculatePayout(userService.findBetCode(, bingoGame.getGameId()),bingoGame.getGameId());
//                        }
                    }
                    break;
                }
            }
        }

        System.out.println("Ticket: " + ticket);
        System.out.println("Columns Left: " + bingoGame.getNumberLeftInColumn());
        System.out.println("Rows Left: " + bingoGame.getNumberLeftInRow());
        System.out.println("Numbers Left to Win: " + bingoGame.getNumberLeftToWin());
    }


    public boolean isBetAlreadyPlaced(int userId, String gameId) {
        try {
            BetDetails betDetails = bingoDB.findUserIdBetAmountByGameId(gameId);
            return betDetails.getUserId() == userId;
        } catch (Exception e) {
            return false;
        }
    }

    public int calculatePayout(String betCode, int betAmount) {
        if (betCode.equals("r1") || betCode.equals("r2") || betCode.equals("r3")) {
            return betAmount * 2;
        } else if (betCode.equals("c1") || betCode.equals("c2") || betCode.equals("c3")) {
            return betAmount * 3;
        }
        return 0;
    }

    public boolean checkIfUserWins(String betCode, List<Integer> ticket) {
        boolean hasWon = false;
        if (betCode.equals("r1") || betCode.equals("r2") || betCode.equals("r3")) {
            int row = Integer.parseInt(betCode.substring(1)) - 1;
            int countMarked = 0;
            for (int i = row * 3; i < (row + 1) * 3; i++) {
                if (ticket.get(i) == -1) {
                    countMarked++;
                }
            }
            if (countMarked == 3) {
                hasWon = true;
            }
        } else if (betCode.equals("c1") || betCode.equals("c2") || betCode.equals("c3")) {
            int column = Integer.parseInt(betCode.substring(1)) - 1;
            int countMarked = 0;
            for (int i = column; i < 9; i += 3) {
                if (ticket.get(i) == -1) {
                    countMarked++;
                }
            }
            if (countMarked == 3) {
                hasWon = true;
            }
        }
        return hasWon;
    }

}
