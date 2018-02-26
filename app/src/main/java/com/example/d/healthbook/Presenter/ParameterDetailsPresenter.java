package com.example.d.healthbook.Presenter;

import android.util.Log;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.ParameterTableResponse;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Week;
import com.example.d.healthbook.Models.DiaryModel.ParameterTypeList;
import com.example.d.healthbook.View.ParametersDetailsView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 27.09.2017.
 */

public class ParameterDetailsPresenter  {
    private ParametersDetailsView viewInterface;

    public ParameterDetailsPresenter(ParametersDetailsView viewInterface) {
        this.viewInterface = viewInterface;
    }

    public void parseData(String raw_data){
        if(raw_data==null|| raw_data.isEmpty())
            return;
        try {
            viewInterface.onDataReady((new Gson().fromJson(raw_data, Week.class)));
        }
        catch (Exception ex){
            Log.e("PARSE error:",ex.getMessage());
        }
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
}
