package com.example.d.healthbook.Presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Chat.AllParticipant;
import com.example.d.healthbook.Models.Chat.Chat;
import com.example.d.healthbook.Models.Chat.ChatResponseData;
import com.example.d.healthbook.Models.Chat.CreateChatResponseData;
import com.example.d.healthbook.Models.Chat.DialogModel;
import com.example.d.healthbook.Models.Chat.DialogsResponseData;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.ChatView;
import com.google.gson.JsonArray;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 24.08.2017.
 */

public class ChatPresenter {
    ChatView viewInterface;
    View root_view;


    private Chat chat_datum;
    public Chat getChat_datum() {
        return chat_datum;
    }

    public void setChat_datum(Chat chat_datum) {
        this.chat_datum = chat_datum;
    }

    private static final int TYPE_UNKNOWN = 0;
    private static final int TYPE_DIALOG = 1;
    private static final int TYPE_GROUP = 2;

    Integer chat_type = TYPE_UNKNOWN;

    public ChatPresenter(ChatView viewInterface , View root_view){
        this.viewInterface = viewInterface;
        this.root_view = root_view;
    }
    public void setData(String data,String chat_id){
        viewInterface.onLoadStatusChanged("Идет загрузка ...");
        if(data!=null)
            setChatData(data);
        else
            getChatData(chat_id);
    }

    private void getChatData(String chat_id){
        Call<ChatResponseData> call =
                App.getApi().getChatById(
                        GlobalVariables.user_auth_token,
                        GlobalVariables.user_id,
                        chat_id);

        call.enqueue(new Callback<ChatResponseData>() {
            @Override
            public void onResponse(Call<ChatResponseData> call, Response<ChatResponseData> response) {
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
                    ChatResponseData data = response.body();
                    if(data.getStatus().equals("ok") &&data.getChat()!=null ){
                        processData(data.getChat());
                    }
                    else{
                        handleError(Empty_ERROR);
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatResponseData> call, Throwable t) {
                //Toast.makeText(getApplication(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                handleError(Server_ERROR);
            }
        });
    }
    private void processData(Chat datum){
        viewInterface.onLoadStatusChanged("Идет обработка данных ...");
//        viewInterface.onDialogsListLoaded(datum);
        this.chat_datum = datum;
        String uid_list ="";
        List<AllParticipant> tmp_participants = datum.getAllParticipants();
        for(AllParticipant tmp_participant:tmp_participants){
            if(!tmp_participant.getId().equals(GlobalVariables.user_id)){
                uid_list +=  tmp_participant.getId().toString()+",";
            }
        }
        uid_list = MainController.checkLastChar(uid_list);
        getChatParticipants(uid_list);
    }

    private void getChatParticipants(String uid_list){
        viewInterface.onLoadStatusChanged("Получаем данные пользователей ...");
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
        viewInterface.onLoadStatusChanged("Обработка данных ...");
        DialogModel dialogs_list = new DialogModel();
            DialogModel dialog_item = new DialogModel();
            dialog_item.setChatData(chat_datum);
            List<ResponseGetUserData> tmp_userlist = new ArrayList<ResponseGetUserData>();
            for (AllParticipant tmp_participants : chat_datum.getAllParticipants()) {
                for (ResponseGetUserData tmp_user:datum) {
                    if(tmp_participants.getId().equals(tmp_user.getId())){
                        tmp_userlist.add(tmp_user);
                    }
                }
            }
        dialog_item.setUsersData(tmp_userlist);
        dialogs_list = (dialog_item);

        viewInterface.onDataLoaded();
        setViewUserData(dialogs_list);
    }

    private void setChatData(String data){
        if(data == null || data.isEmpty()){
            viewInterface.onUserLoadError();
        }
        else{
            DialogModel dataObj = MainController.deSerializeToJson(data,DialogModel.class);
            setViewUserData(dataObj);
            viewInterface.onUserDataLoaded(dataObj);
            //chat_datum = dataObj;
            chat_datum = dataObj.getChatData();

            viewInterface.onDataLoaded();
        }
    }
    private void setViewUserData( DialogModel dataObj ){
        chat_type = dataObj.getChatData().getType().equals("dialog") ? TYPE_DIALOG : TYPE_GROUP;
        final List<ResponseGetUserData> usersData = new ArrayList<ResponseGetUserData>();
        Chat chatData = dataObj.getChatData();
        String usersList="";
        for (ResponseGetUserData tmp_user : dataObj.getUsersData()) {
            if(tmp_user.getId() != GlobalVariables.user_id){
                usersData.add(tmp_user);
                usersList += String.format("%s %s %s", MainController.stringChecker(usersData.get(0).getName())
                            , MainController.stringChecker(usersData.get(0).getMiddleName())
                            , MainController.stringChecker(usersData.get(0).getSurname()))+",";
            }
        }
        String chatName = chatData.getName()!=null ?
                chatData.getName() : (chatData.getAllParticipants().size()<=2 ? (String.format("%s %s %s",
                MainController.stringChecker(usersData.get(0).getName())
                , MainController.stringChecker(usersData.get(0).getMiddleName())
                , MainController.stringChecker(usersData.get(0).getSurname()))): "Группа без имени");
        if(chatData.getAllParticipants().size()<=2){
            MainController.setImageToViewById((Activity)viewInterface,usersData.get(0).getPhoto(),((RoundedImageView) root_view.findViewById(R.id.profileAvaChatPreviewRIV)));
        }
        ((TextView) root_view.findViewById(R.id.toolbar_title)).setText(chatName);
        ((TextView) root_view.findViewById(R.id.people_chat_TV)).setText( MainController.checkLastChar(usersList));
      //  MainController.loadImageToView((Activity)viewInterface , usersData.get(0).getPhone() ,((RoundedImageView) root_view.findViewById(R.id.profileAvaChatPreviewRIV)));
        if(chat_type == TYPE_GROUP) {
            ((LinearLayout) root_view.findViewById(R.id.chat_info_container))
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<String> users_id = getUsersId(usersData);
                            viewInterface.onEditButtonClicked(chat_datum.getId() , chat_datum.getName(),users_id);
                        }
                    });
        }
    }

    public void editGroup(String group_name,String[] expert_ids ){
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
        editGroupRequest( chat_datum.getId(), group_name ,retData.toString() );
    }

    private void editGroupRequest( String chat_id , String group_name, String participants) {
        Call<CreateChatResponseData> call =
                App.getApi().editChat(
                        GlobalVariables.user_auth_token,
                        GlobalVariables.user_id,
                        chat_id,
                        group_name,
                        participants);

        call.enqueue(new Callback<CreateChatResponseData>() {
            @Override
            public void onResponse(Call<CreateChatResponseData> call, Response<CreateChatResponseData> response) {
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
                    CreateChatResponseData datum = response.body();
                    if(datum.getStatus().equals("ok")){
                        String tmp_chat = datum.getChatId()==null || datum.getChatId().isEmpty() ? chat_datum.getId() : datum.getChatId();
                        getChatData(tmp_chat);
                    }
                    else{
                        handleError(Data_ERROR);
                    }

                }
            }

            @Override
            public void onFailure(Call<CreateChatResponseData> call, Throwable t) {
                //Toast.makeText(getApplication(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                handleError(Server_ERROR);
            }
        });
    }

    List<String> getUsersId(List<ResponseGetUserData> data){
        List<String> users_id = new ArrayList<String>();
        for(ResponseGetUserData tmp_user :data){
            if(tmp_user.getId()!=GlobalVariables.user_id)
                users_id.add(tmp_user.getId().toString());
        }
        return users_id;
    }
    private static final int Server_ERROR = 10;
    private static final int Network_ERROR = 11;
    private static final int Data_ERROR = 12;
    private static final int Empty_ERROR = 13;
    private static final int Request_ERROR = 14;

    private void handleError(int type){
        String error_reason="";
        switch (type){
            case Server_ERROR:{
                error_reason = "Ошибка на сервере";
                break;
            }
            case Network_ERROR:{
                error_reason = "Ошибка с сетю";
                break;
            }
            case Data_ERROR:{
                error_reason = "Не удалось получить данные";
                break;
            }
            case Empty_ERROR:{
                error_reason = "Список пуст";
                break;
            }
            case Request_ERROR:{
                error_reason = "Не удалось получить ответ от сервера";
                break;
            }
        }
        viewInterface.onDataLoadError(error_reason);
    }
}
