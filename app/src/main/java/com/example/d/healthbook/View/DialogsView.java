package com.example.d.healthbook.View;

import com.example.d.healthbook.Models.Chat.Chat;
import com.example.d.healthbook.Models.Chat.DialogModel;

import java.util.List;

/**
 * Created by User on 21.08.2017.
 */

public interface DialogsView extends BaseDialogsView {
    void onDialogsListLoaded(List<DialogModel> data);
    void onNewMessage();

    void onProgressChanged(String progress_type);

    void onDialogClicked(Integer position);

    void onChatCreated(String chat_id);

    void onNewChatDataLoaded(DialogModel data);
}
