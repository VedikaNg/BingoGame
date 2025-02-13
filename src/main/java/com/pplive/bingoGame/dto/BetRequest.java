package com.pplive.bingoGame.dto;

import org.springframework.lang.NonNull;

public class BetRequest {
    @NonNull
    private int userId;
    @NonNull
    private String gameId;
    @NonNull
    private String betCode;
    @NonNull
    private int betAmount;

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public void setBetCode(String betCode) {
        this.betCode = betCode;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public int getUserId() {
        return userId;
    }

    public String getBetCode() {
        return betCode;
    }
}



