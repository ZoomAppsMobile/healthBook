package com.example.d.healthbook.View;

import com.example.d.healthbook.Models.ResponseDoctorInfo;

/**
 * Created by D on 02.06.2017.
 */

public interface DoctorInterface extends BaseDialogsView {
    ResponseDoctorInfo getData();
    void onChatCreated(String chat_id);
}
