package com.example.d.healthbook.Models.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public class ChatResponseData {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("chat")
    @Expose
    private Chat chat;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}

