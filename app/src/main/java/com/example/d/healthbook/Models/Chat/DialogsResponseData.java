package com.example.d.healthbook.Models.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 21.08.2017.
 */

public class DialogsResponseData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("chats")
    @Expose
    private List<Chat> chats = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
