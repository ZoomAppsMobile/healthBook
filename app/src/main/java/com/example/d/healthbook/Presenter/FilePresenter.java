package com.example.d.healthbook.Presenter;

import android.util.Log;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.DiaryModel.CompleteDiaryData;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.ParameterTableResponse;
import com.example.d.healthbook.Models.DiaryModel.ParameterTypeList;
import com.example.d.healthbook.Models.DiaryModel.ParametersResponse;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.View.FileView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 19.09.2017.
 */

public class FilePresenter {
    FileView viewInterface;

    public FilePresenter(FileView viewInterface){
        this.viewInterface = viewInterface;
    }

    public void parseData(String raw_json){
        if(raw_json==null)
        {
            showFatalError("Не удалось получить ланные");
            return;
        }

        DocumentMyFamily data = new Gson().fromJson( raw_json ,DocumentMyFamily.class);
        if(data!=null) {
            processData(data);
        }
        else
            showFatalError("Не удалось получить ланные");
    }

    private void processData(DocumentMyFamily data){
        viewInterface.onSetViewData(data);
        loadFullData(data.getId());
        loadParametersData(data.getId());
        Log.d("FSDFSDFDSFDS",data.getId());
        loadParametersTypes();
        loadParametersTable(data.getId());
    }

    private void loadParametersTable(String id) {
        Call<ParameterTableResponse> call = App.getApi().getFolderParameterTable(id,GlobalVariables.user_auth_token);
        call.enqueue(new Callback<ParameterTableResponse>() {
            @Override
            public void onResponse(Call<ParameterTableResponse> call, Response<ParameterTableResponse> response) {
                if(response==null || response.body()==null )
                    return;

                ParameterTableResponse body = response.body();
                if(body.getWeek()!=null)
                    viewInterface.onTableDataLoaded(body.getWeek());
            }

            @Override
            public void onFailure(Call<ParameterTableResponse> call, Throwable t) {

            }
        });
    }

    private void loadParametersTypes() {
        Call<List<ParameterTypeList>> call = App.getApi().getParameterTypes(GlobalVariables.user_auth_token);
        call.enqueue(new Callback<List<ParameterTypeList>>() {
            @Override
            public void onResponse(Call<List<ParameterTypeList>> call, Response<List<ParameterTypeList>> response) {
                if(response==null || response.body()==null || response.body().isEmpty())
                    return;
                //TODO:CALLBACK
                List<ParameterTypeList> body = response.body();
            }

            @Override
            public void onFailure(Call<List<ParameterTypeList>> call, Throwable t) {

            }
        });
    }


    private void loadParametersData(String id) {
        Call<ParametersResponse> call = App.getApi().getFolderParameter(id,GlobalVariables.user_auth_token);
        call.enqueue(new Callback<ParametersResponse>() {
            @Override
            public void onResponse(Call<ParametersResponse> call, Response<ParametersResponse> response) {
                if(response==null || response.body()==null )
                    return;
                try {
                    String result = response.raw().body().string()  ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                viewInterface.onParametersLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ParametersResponse> call, Throwable t) {

            }
        });
    }

    private void loadFullData(String id){
        Call<CompleteDiaryData> call =
                App.getApi().getDiaryData(
                        GlobalVariables.user_auth_token,
                        GlobalVariables.user_id,
                        id);
        call.enqueue(new Callback<CompleteDiaryData>() {
            @Override
            public void onResponse(Call<CompleteDiaryData> call, Response<CompleteDiaryData> response) {
                if(response==null || response.body()==null ) {
                    showError();
                    return;
                }
                viewInterface.onFullDataLoaded(response.body());
            }

            @Override
            public void onFailure(Call<CompleteDiaryData> call, Throwable t) {
                showError();
            }
        });
    }
    private void showError(){
        viewInterface.onShowMessage("Не удалось получить ланные");
    }
    private void showError(String message){
        viewInterface.onShowMessage(message);
    }
    private void showFatalError(String message){
        viewInterface.onShowMessage(message);
        viewInterface.onFatalError();
    }
}
