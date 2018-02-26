package com.example.d.healthbook.Presenter;

import android.util.Log;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.UserActivityInfo;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Chat.AllParticipant;
import com.example.d.healthbook.Models.Chat.Chat;
import com.example.d.healthbook.Models.Chat.CreateChatResponseData;
import com.example.d.healthbook.Models.Chat.DialogModel;
import com.example.d.healthbook.Models.Chat.DialogsResponseData;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.View.DialogsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 21.08.2017.
 */

public class DialogsPresenter extends BaseDialogsPresenter{
    DialogsView viewInterface;
    private List<Chat> chat_datum;
    public DialogsPresenter(DialogsView viewInterface){
        super(viewInterface);
        this.viewInterface = viewInterface;
    }


    public void getDialogs(){
        onProgressChanged(chat_load_started);
        Call<DialogsResponseData> call =
                App.getApi().getDialogsList(
                                GlobalVariables.user_auth_token,
                                GlobalVariables.user_id,
                                GlobalVariables.user_id,
                                "r-client");

        call.enqueue(new Callback<DialogsResponseData>() {
            @Override
            public void onResponse(Call<DialogsResponseData> call, Response<DialogsResponseData> response) {
                if(response.code()!=200){
                    handleError(Request_ERROR);
                }
                else if(response.errorBody()!=null){
                    handleError(Server_ERROR);
                }
                else if(response.body()==null){
                    handleError(Data_ERROR);
                }
                else if(response.body()!=null){
                    DialogsResponseData data = response.body();
                    if(data.getStatus().equals("ok") &&data.getChats()!=null &&!data.getChats().isEmpty()){
                        processData(data.getChats());
                    }
                    else{
                        handleError(Empty_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<DialogsResponseData> call, Throwable t) {
                //Toast.makeText(getApplication(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                handleError(Server_ERROR);
            }
        });
    }

    private void processData(List<Chat> datum){
//        viewInterface.onDialogsListLoaded(datum);
        onProgressChanged(chet_proccess_started);
        this.chat_datum = datum;
        String uid_list ="";
        for (Chat tmp_chat:datum) {
            List<AllParticipant> tmp_participants = tmp_chat.getAllParticipants();
            for(AllParticipant tmp_participant:tmp_participants){
                if(!tmp_participant.getId().equals(GlobalVariables.user_id)){
                    uid_list +=  tmp_participant.getId().toString()+",";
                }
            }
        }
        uid_list = MainController.checkLastChar(uid_list);
        getChatParticipants(uid_list);
    }

    private void getChatParticipants(String uid_list){
        onProgressChanged(user_load_started);
        Call<List<ResponseGetUserData>> call =
                App.getApi().getDialogParticipantsList(
                        GlobalVariables.user_auth_token,
                        uid_list);

        call.enqueue(new Callback<List<ResponseGetUserData>>() {
            @Override
            public void onResponse(Call<List<ResponseGetUserData>> call, Response<List<ResponseGetUserData>> response) {
                if(response.code()!=200){
                    handleError(Request_ERROR);
                }
                else if(response.errorBody()!=null){
                    handleError(Server_ERROR);
                }
                else if(response.body()==null){
                    handleError(Data_ERROR);
                }
                else if(response.body()!=null){
                    List<ResponseGetUserData> datum = response.body();
                    if(!datum.isEmpty()){
                        processModelDatum(datum);
                    }
                    else{
                        handleError(Empty_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResponseGetUserData>> call, Throwable t) {
                //Toast.makeText(getApplication(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                handleError(Server_ERROR);
            }
        });
    }

    private void processModelDatum( List<ResponseGetUserData> datum ){
        onProgressChanged(user_proccess_started);
        List<DialogModel> dialogs_list = new ArrayList<DialogModel>();
        for (Chat tmp_chat:chat_datum) {
            DialogModel dialog_item = new DialogModel();
            dialog_item.setChatData(tmp_chat);
            List<ResponseGetUserData> tmp_userlist = new ArrayList<ResponseGetUserData>();
            for (AllParticipant tmp_participants : tmp_chat.getAllParticipants()) {
                for (ResponseGetUserData tmp_user:datum) {
                    if(tmp_participants.getId().equals(tmp_user.getId())){
                        tmp_userlist.add(tmp_user);
                    }
                }
            }
            dialog_item.setUsersData(tmp_userlist);
            dialogs_list.add(dialog_item);
        }
        viewInterface.onDialogsListLoaded(dialogs_list);
    }

    private JsonArray createParticipantsList(List<Integer> expert_ids){
        JsonArray retData = new JsonArray();
        for (Integer cur_id:expert_ids) {
            retData.add(API_Controller.createParticipants(cur_id,"r-expert"));
        }
        return retData;
    }

    public void addChat(Integer expert_id ){
        String chat_type="dialog";
        String chat_name ="";
        JsonArray retData = new JsonArray();
        retData.add(API_Controller.createParticipants(expert_id,"r-expert"));
        retData.add(API_Controller.createParticipants(GlobalVariables.user_id,"r-client"));
        onProgressChanged(add_chat_started);
        addChatRequest(chat_type,chat_name,retData.toString() );
    }
    public void addGroup(String group_name,String[] expert_ids ){
        List<Integer> ids = new ArrayList<>();
        for (String tmp_id: expert_ids) {
            ids.add(Integer.parseInt(tmp_id));
        }
        String chat_type="group";
        JsonArray retData = new JsonArray();
        for (Integer expert_id:ids) {
            retData.add(API_Controller.createParticipants(expert_id,"r-expert"));
        }
        retData.add(API_Controller.createParticipants(GlobalVariables.user_id,"r-client"));
        addChatRequest(chat_type, group_name,retData.toString() );
    }


    public void addChat(String name , List<String> experts ){

    }



    private void onProgressChanged(int type){
        String  message = "";
        switch (type){
             case chat_load_started:{
                 message = "Загрузка списка дилогов...";
                 break;
             }
             case chet_proccess_started:{
                 message = "Обработка списка дилогов...";
                 break;
             }
             case user_load_started:{
                 message = "Загрузка списка пользователей...";
                 break;
             }
             case user_proccess_started:{
                 message = "Обработка списка пользователей...";
                 break;
             }
            case add_chat_started:{
                message = "Создание чата...";
                break;
            }
        }

        viewInterface.onProgressChanged(message);
    }




}
