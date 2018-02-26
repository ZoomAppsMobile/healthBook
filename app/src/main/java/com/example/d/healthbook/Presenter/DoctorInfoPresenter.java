package com.example.d.healthbook.Presenter;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.View.BaseDialogsView;
import com.example.d.healthbook.View.DoctorInterface;
import com.google.gson.JsonArray;

/**
 * Created by User on 31.08.2017.
 */

public class DoctorInfoPresenter extends BaseDialogsPresenter {
    public DoctorInfoPresenter(DoctorInterface viewInterface) {
        super(viewInterface);
    }

    public void getChat(Integer expert_id){
        JsonArray retData = new JsonArray();
        retData.add(API_Controller.createParticipants(expert_id,"r-expert"));
        retData.add(API_Controller.createParticipants(GlobalVariables.user_id,"r-client"));
        super.addChatRequest("dialog","",retData.toString());
    }
}
