package com.pplive.bingoGame.dto;

public class ResultRequest {

    private String gameId;
    private int randomNumber;

    public String getGameId() {
        return gameId;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }
}
