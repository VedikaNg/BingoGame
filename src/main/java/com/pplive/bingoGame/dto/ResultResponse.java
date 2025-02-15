package com.pplive.bingoGame.dto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ResultResponse {

    private String gameId;
    private Set<Integer> numberLeftToWin;
    private List<Integer> numberSequence;
    private int payout;

    public String getGameId() {
        return gameId;
    }

    public int getPayout() {
        return payout;
    }

    public Set<Integer> getNumberLeftToWin() {
        return numberLeftToWin;
    }

    public List<Integer> getNumberSequence() {
        return numberSequence;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setNumberLeftToWin(Set<Integer> numberLeftToWin) {
        this.numberLeftToWin = numberLeftToWin;
    }

    public void setNumberSequence(List<Integer> sequence) {
        this.numberSequence=sequence;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }
}
