package com.example.mctapp.Models;

public class Chat {
    int id;

    int user_id;

    String text_sender;
    int receiver;

    String msg_r;


    public int getId() {
        return id;
    }


    public Chat(int id, String text_sender, String msg_r) {
        this.id = id;
        this.text_sender = text_sender;
        this.msg_r = msg_r;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getReceiver() {
        return receiver;
    }


    public String getMsg_r() {
        return msg_r;
    }

    public String getText_sender() {
        return text_sender;
    }
}
