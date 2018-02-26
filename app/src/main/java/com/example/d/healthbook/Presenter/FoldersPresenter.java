package com.example.d.healthbook.Presenter;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Fragments.FoldersFragment;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.Models.ResponseMyFamilyMembers;
import com.example.d.healthbook.View.FoldersView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 21.09.2017.
 */

public class FoldersPresenter {

    private FoldersView viewInterface;

    public FoldersPresenter(FoldersView viewInterface) {
        this.viewInterface = viewInterface;
    }

    public void getData(){
        App.getApi().seeFamilyMembers(GlobalVariables.user_auth_token).enqueue(new Callback<ResponseMyFamilyMembers>() {
            @Override
            public void onResponse(Call<ResponseMyFamilyMembers> call, Response<ResponseMyFamilyMembers> response) {
                int s = response.code();
               if(response.body()!=null){
                   if(response.body().getDocuments()==null || response.body().getDocuments().isEmpty()){
                       viewInterface.onShowError("Пусто");
                   }
                   else{
                       viewInterface.onDataLoaded(response.body().getDocuments(),DocumentMyFamily.class);
                   }
               }
            }

            @Override
            public void onFailure(Call<ResponseMyFamilyMembers> call, Throwable t) {
//                Toast.makeText(UserActivityInfo.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
