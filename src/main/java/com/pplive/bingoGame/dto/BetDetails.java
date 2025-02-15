package com.pplive.bingoGame.dto;

public class BetDetails {
    private int userId;
    private int betAmount;

    public BetDetails(int userId, int betAmount) {
        this.userId = userId;
        this.betAmount = betAmount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
