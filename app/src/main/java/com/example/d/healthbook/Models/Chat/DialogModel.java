package com.example.d.healthbook.Models.Chat;

import com.example.d.healthbook.Models.ResponseGetUserData;

import java.util.List;

/**
 * Created by User on 21.08.2017.
 */

public class DialogModel {
    List<ResponseGetUserData> usersData;
    Chat chatData;

    public List<ResponseGetUserData> getUsersData() {
        return usersData;
    }

    public void setUsersData(List<ResponseGetUserData> userData) {
        this.usersData = userData;
    }

    public Chat getChatData() {
        return chatData;
    }

    public void setChatData(Chat chatData) {
        this.chatData = chatData;
    }
}
