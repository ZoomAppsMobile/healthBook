package com.example.d.healthbook.Presenter;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Chat.CreateChatResponseData;
import com.example.d.healthbook.View.BaseDialogsView;
import com.example.d.healthbook.View.DialogsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 31.08.2017.
 */

abstract class BaseDialogsPresenter {
    private BaseDialogsView viewInterface;
    public BaseDialogsPresenter(BaseDialogsView viewInterface){
        this.viewInterface = viewInterface;
    }

    protected void addChatRequest(String chat_type ,String chat_name  ,String participants){

        Call<CreateChatResponseData> call =
                App.getApi().addChat(
                        GlobalVariables.user_auth_token,
                        GlobalVariables.user_id,
                        chat_type,
                        chat_name,
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
                        viewInterface.onChatCreated(datum.getChatId());
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

    protected static final int chat_load_started = 1;
    protected static final int chet_proccess_started = 2;
    protected static final int user_load_started = 3;
    protected static final int user_proccess_started = 4;
    protected static final int add_chat_started = 5;
    protected void handleError(int type){
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
        viewInterface.onDialogsListLoadError(error_reason);
    }

    protected static final int Server_ERROR = 10;
    protected static final int Network_ERROR = 11;
    protected static final int Data_ERROR = 12;
    protected static final int Empty_ERROR = 13;
    protected static final int Request_ERROR = 14;
}
