package com.pplive.bingoGame.service;

import com.pplive.bingoGame.repository.BingoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    BingoDB bingoDB;

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


    public int findBetCode(int userId, String gameId){
       return bingoDB.findBetCodeByUserIdGameId(userId, gameId);
    }


}
