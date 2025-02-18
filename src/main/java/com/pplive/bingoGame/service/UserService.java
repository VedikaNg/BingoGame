package com.pplive.bingoGame.service;

import com.pplive.bingoGame.BingoGameApplication;
import com.pplive.bingoGame.dto.BetDetails;
import com.pplive.bingoGame.dto.BingoGame;
import com.pplive.bingoGame.repository.BingoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    BingoDB bingoDB;

    @Autowired
    @Lazy
    BingoGame bingoGame;


    public boolean checkBalance(int betAmount, int userId, String betCode, String gameId){
        int balance= bingoDB.findBalanceByUserId(userId);
        if(balance>=betAmount){
            bingoDB.updateBalanceInDB(balance-betAmount, userId);
            bingoDB.insertBingoGameIdIntoDB(gameId);
            bingoDB.insertIntoBetTable(betCode, betAmount, userId, gameId);
            return true;
        }
        return false;
    }


    public int findBetCode(String gameId){
       BetDetails betDetails = bingoDB.findUserIdBetAmountBetCodeByGameId(gameId);
       return betDetails.getBetCode();
    }

    public int findBetAmount(String gameId){
        return bingoDB.findUserIdBetAmountBetCodeByGameId(gameId).getBetAmount();
    }

    public boolean isBetAlreadyPlaced(int userId, String gameId) {
        try {
            BetDetails betDetails = bingoDB.findUserIdBetAmountBetCodeByGameId(gameId);
            return betDetails.getUserId() == userId;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkNumberInTicket(int randomNumber, List<Integer> ticket, BingoGame bingoGame) {
        bingoGame.setGameId(bingoGame.getGameId());
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

                    if (columnCount - 1 == 0 || rowCount - 1 == 0) {
                        if(columnCount-1 ==0){
                            bingoGame.setWinningBetCode(2104);
                        }
                        else{
                            bingoGame.setWinningBetCode(2101+i);
                        }

                        System.out.println(checkIfUserWins(findBetCode(bingoGame.getGameId()),bingoGame.getWinningBetCode()));
                        if(checkIfUserWins(findBetCode(bingoGame.getGameId()),bingoGame.getWinningBetCode()))
                        {
                            bingoGame.setPayout(calculatePayout(findBetCode(bingoGame.getGameId()), findBetAmount(bingoGame.getGameId())));
                            System.out.println("Payout: "+bingoGame.getPayout());
                        }
                        return true;
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
                        if(columnCount-1 ==0){
                            bingoGame.setWinningBetCode(2105);
                        }
                        else{
                            bingoGame.setWinningBetCode(2101+i-3);
                        }
                        if(checkIfUserWins(findBetCode(bingoGame.getGameId()),bingoGame.getWinningBetCode()))
                        {
                            bingoGame.setPayout(calculatePayout(findBetCode(bingoGame.getGameId()), findBetAmount(bingoGame.getGameId())));
                        }
                        return true;
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
                        if(columnCount-1 ==0){
                            bingoGame.setWinningBetCode(2106);
                        }
                        else{
                            bingoGame.setWinningBetCode(2101+i-6);
                        }
                        if(checkIfUserWins(findBetCode(bingoGame.getGameId()),bingoGame.getWinningBetCode()))
                        {
                            bingoGame.setPayout(calculatePayout(findBetCode(bingoGame.getGameId()), findBetAmount(bingoGame.getGameId())));
                        }
                        return true;
                    }
                    break;
                }
            }
        }

        System.out.println("Ticket: " + ticket);
        System.out.println("Columns Left: " + bingoGame.getNumberLeftInColumn());
        System.out.println("Rows Left: " + bingoGame.getNumberLeftInRow());
        System.out.println("Numbers Left to Win: " + bingoGame.getNumberLeftToWin());
        return false;
    }

    public int calculatePayout(int betCode, int betAmount) {
        if (betCode>=2101 && betCode<=2103) {
            return betAmount * 2;
        } else if (betCode>=2104 && betCode<=2106) {
            return betAmount * 3;
        }
        return 0;
    }

    public boolean checkIfUserWins(int betCode, int winningBetCode) {
        return betCode == winningBetCode;
    }




}
