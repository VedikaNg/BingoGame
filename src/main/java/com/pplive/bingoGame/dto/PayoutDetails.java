package com.pplive.bingoGame.dto;

public class PayoutDetails {
    private String gameId;
    private int payout;

    public void setPayout(int payout) {
        this.payout = payout;
    }

    public int getPayout() {
        return payout;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
