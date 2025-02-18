package com.pplive.bingoGame.dto;

public class SendMessage {

    private String message;

    public SendMessage(String msg){
        this.message=msg;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
