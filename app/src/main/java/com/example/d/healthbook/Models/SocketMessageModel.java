
package com.example.d.healthbook.Models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SocketMessageModel {

    @SerializedName("chat_id")
    private String mChatId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("from_user")
    private Integer mFromUser;
    @SerializedName("id")
    private String mId;
    @SerializedName("read")
    private List<Object> mRead;
    @SerializedName("text")
    private String mText;
    @SerializedName("to_users")
    private List<Integer> mToUsers;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public String getChatId() {
        return mChatId;
    }

    public void setChatId(String chatId) {
        mChatId = chatId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Integer getFromUser() {
        return mFromUser;
    }

    public void setFromUser(Integer fromUser) {
        mFromUser = fromUser;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<Object> getRead() {
        return mRead;
    }

    public void setRead(List<Object> read) {
        mRead = read;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public List<Integer> getToUsers() {
        return mToUsers;
    }

    public void setToUsers(List<Integer> toUsers) {
        mToUsers = toUsers;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
