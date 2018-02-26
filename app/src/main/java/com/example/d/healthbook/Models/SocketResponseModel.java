
package com.example.d.healthbook.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SocketResponseModel {

    @SerializedName("messages")
    private List<SocketMessageModel> mMessages;
    @SerializedName("result")
    private String mResult;
    @SerializedName("type")
    private String mType;
    @SerializedName("text")
    private String mText;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("chat_id")
    private String mChatId;
    @SerializedName("from_user")
    private Integer mFromUser;
    @SerializedName("to_users")
    private List<Integer> mToUsers;

    public List<SocketMessageModel> getMessages() {
        return mMessages;
    }

    public void setMessages(List<SocketMessageModel> messages) {
        mMessages = messages;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public String getChatId() {
        return mChatId;
    }

    public void setChatId(String mChatId) {
        this.mChatId = mChatId;
    }

    public Integer getFromUser() {
        return mFromUser;
    }

    public void setFromUser(Integer mFromUser) {
        this.mFromUser = mFromUser;
    }

    public List<Integer> getToUsers() {
        return mToUsers;
    }

    public void setToUsers(List<Integer> toUsers) {
        this.mToUsers = toUsers;
    }
}
