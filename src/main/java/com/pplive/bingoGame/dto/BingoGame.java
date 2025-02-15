package com.pplive.bingoGame.dto;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
public class BingoGame {
    private String gameId;
    private Boolean gameStatus=false;
    private Boolean betsOpen=false;
    private List<Integer> ticket;
    private Instant gameStartTime;
    private List<Integer> numberSequence = new ArrayList<>();
    private  List<Integer> numberLeftInRow= Arrays.asList(3,3,3);
    private List<Integer> numberLeftInColumn=Arrays.asList(3,3,3);
    private Set<Integer> numberLeftToWin = new HashSet<>();

    public Instant getGameStartTime() {
        if (gameStartTime == null) {
            throw new IllegalStateException("Game start time is not initialized");
        }
        return gameStartTime;
    }

    public List<Integer> getNumberLeftInColumn() {
        return numberLeftInColumn;
    }

    public List<Integer> getNumberLeftInRow() {
        return numberLeftInRow;
    }

    public Set<Integer> getNumberLeftToWin() {
        return numberLeftToWin;
    }

    public Boolean getBetsOpen() {
        return betsOpen;
    }

    public List<Integer> getNumberSequence() {
        return numberSequence;
    }

    public Boolean getGameStatus() {
        return gameStatus;
    }

    public Boolean getBetsOpen(Instant time) {
        if (time == null) {
            throw new IllegalArgumentException("The 'time' parameter cannot be null");
        }
        Duration difference = Duration.between(getGameStartTime(), time);
        if(difference.toSeconds()<90){
            return true;
        }
        return false;
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

    public void setNumberSequence(List<Integer> numberSequence) {
        this.numberSequence = numberSequence;
    }

    public void setNumberLeftToWin(Set<Integer> numberLeftToWin) {
        this.numberLeftToWin = numberLeftToWin;
    }

    public void setNumberLeftInColumn(List<Integer> numberLeftInColumn) {
        this.numberLeftInColumn = numberLeftInColumn;
    }

    public void setNumberLeftInRow(List<Integer> numberLeftInRow) {
        this.numberLeftInRow = numberLeftInRow;
    }
}
