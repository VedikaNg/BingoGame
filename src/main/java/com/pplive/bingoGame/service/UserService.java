package com.pplive.bingoGame.service;

import com.pplive.bingoGame.repository.BingoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    BingoDB bingoDB;


    public boolean checkBalance(int betAmount, int userId){
        double balance= bingoDB.findBalanceByUserId(userId);
        if(balance>=betAmount){
            return true;
        }
        return false;
    }


}
