package com.pplive.bingoGame.dto;

public class BetResponse {
    private String betId;
    private int userId;
    private boolean betValid;
    private String gameId;

    public String getBetId() {
        return betId;
    }

    public String getGameId() {
        return gameId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isBetValid() {
        return betValid;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setBetValid(boolean betValid) {
        this.betValid = betValid;
    }

    public void setBetId(String betId) {
        this.betId = betId;
    }
}
