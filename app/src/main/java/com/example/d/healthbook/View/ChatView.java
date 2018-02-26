package com.example.d.healthbook.View;

import com.example.d.healthbook.Models.Chat.DialogModel;
import com.example.d.healthbook.Models.SocketMessageModel;
import com.example.d.healthbook.Models.SocketResponseModel;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface ChatView {
    void onSocketConnected();
    void onDataLoaded();

    void onLoadStatusChanged(String message);
    void onDataLoadError(String reason);
    void onUserLoadError();
    void onUserDataLoaded(DialogModel dataObj);

    void onGetMessage(List<SocketMessageModel> messages);

    void onEditButtonClicked(String chat_id , String chat_name , List<String> user_ids);
    void onMessageReceived(SocketMessageModel message);
}
