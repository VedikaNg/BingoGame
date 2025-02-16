package com.pplive.bingoGame.dto;


public class BetDetails {
    private int userId;
    private int betAmount;
    private int betCode;

    public BetDetails(int userId, int betAmount, int betCode) {
        this.userId = userId;
        this.betAmount = betAmount;
        this.betCode = betCode;
    }

    public int getBetCode() {
        return betCode;
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
