package com.example.d.healthbook.Models.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 24.08.2017.
 */

public class CreateChatResponseData {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("chat_id")
    @Expose
    private String chatId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
