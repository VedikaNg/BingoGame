package com.pplive.bingoGame.dto;

import java.time.Instant;
import java.util.List;

public class BingoGame {
    private String gameId;
    private Boolean gameStatus;
    private Boolean betsOpen;
    private List<Integer> ticket;
    private Instant gameStartTime;

    public Instant getGameStartTime(Instant now) {
        return gameStartTime;
    }

    public Boolean getGameStatus() {
        return gameStatus;
    }

    public Boolean getBetsOpen() {
        return betsOpen;
    }

    public String getGameId() {
        return gameId;
    }

    public List<Integer> getTicket() {
        return ticket;
    }

    public void setBetsOpen(Boolean betsOpen) {
        this.betsOpen = betsOpen;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setGameStatus(Boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setTicket(List<Integer> ticket) {
        this.ticket = ticket;
    }

    public void setGameStartTime(Instant gameStartTime) {
        this.gameStartTime = gameStartTime;
    }
}
