package com.example.d.healthbook.Presenter;

import android.util.Log;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseDoctorList;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.Models.ResponseSubscribe;
import com.example.d.healthbook.Models.SubsriptionCheckResponseData;
import com.example.d.healthbook.View.ChannelInfoView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 31.08.2017.
 */

public class ChannelInfoPresenter {
    private ChannelInfoView viewInterface;
    private Boolean isSubscribed;
    public ChannelInfoPresenter(ChannelInfoView viewInterface){
        this.viewInterface = viewInterface;
    }

    public void getData(String channel_id){
        checkSubscription(channel_id);
        getNewsOfChannel(channel_id);
    }

    private void checkSubscription(String channel_id){
        Call<SubsriptionCheckResponseData> call =
                App.getApi().checkChannelSubscription(
                        GlobalVariables.user_auth_token,
                        GlobalVariables.user_id,
                        channel_id,
                        "channel");
        call.enqueue(new Callback<SubsriptionCheckResponseData>() {

            @Override
            public void onResponse(Call<SubsriptionCheckResponseData> call, Response<SubsriptionCheckResponseData> response) {
                if(response.errorBody()==null && response.body()!=null){
                    onSubcribedChanged(response.body().getIsSubscribed());
                }
                else{
                    viewInterface.onLoadedError("Не удалось получить данные");
                }
            }

            @Override
            public void onFailure(Call<SubsriptionCheckResponseData> call, Throwable t) {
                viewInterface.onLoadedError("Не удалось получить данные");
            }
        });
    }
    void onSubcribedChanged(Boolean isSubscribed){
        this.isSubscribed = isSubscribed;
        if(isSubscribed){
            viewInterface.onSubscribed();
        }
        else{
            viewInterface.onUnSubscribed();
        }
    }
    private void getNewsOfChannel(String channel_id){
        Call<ResponseDoctorList> call =
                App.getApi().getChannelNews(
                        GlobalVariables.user_auth_token,
                        GlobalVariables.user_id,
                        channel_id);

        call.enqueue(new Callback<ResponseDoctorList>() {
            @Override
            public void onResponse(Call<ResponseDoctorList> call, Response<ResponseDoctorList> response) {
                if(response.errorBody()==null && response.body()!=null){
                    viewInterface.onDataLoaded(response.body().getDocuments());
                }
                else{
                    viewInterface.onLoadedError("Не удалось получить данные");
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                viewInterface.onLoadedError("Не удалось получить данные");
            }
        });
    }

    public void actionSubscribe(Integer type, String id){
        if(isSubscribed){
            unSubscribeAction(type,id);
        }
        else{
            subscribeAction(type,id);
        }
    }

    private void unSubscribeAction(Integer type, String id) {
        App.getApi().unSubscribe(GlobalVariables.user_auth_token,id,type.toString(), API_Controller.subscribeJson(type, id))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                            onSubcribedChanged(false);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    private void subscribeAction(Integer type, String id) {
        App.getApi().subscribe( GlobalVariables.user_auth_token, API_Controller.subscribeJson(type, id))
                .enqueue(new Callback<ResponseSubscribe>() {
            @Override
            public void onResponse(Call<ResponseSubscribe> call, Response<ResponseSubscribe> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ResponseSubscribe registeredUser= response.body();

                viewInterface.onSubscribed();
                onSubcribedChanged(true);

            }

            @Override
            public void onFailure(Call<ResponseSubscribe> call, Throwable t) {
               viewInterface.onLoadedError("An error occurred during networking");
            }
        });
    }

}
