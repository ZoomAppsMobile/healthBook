package com.example.d.healthbook.FragmentsTab;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.FileActivity;
import com.example.d.healthbook.Adapters.RecyclerAdapterMyFamilyMembers;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Fragments.DialogFragmentCreateMemberOfFamily;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.Models.ResponseMyFamilyMembers;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.AdapterInteraction;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.d.healthbook.Activities.UserActivityInfo.seeUserFamilyMembers;
import static com.example.d.healthbook.GlobalVariables.GlobalVariables.responseGetUserData;

/**
 * Created by D on 01.07.2017.
 */

public class TabUserInfoMyFamily extends Fragment implements AdapterInteraction {


    private boolean isViewCreated = false;
    private ResponseMyFamilyMembers mainData;
    private RecyclerView mRecyclerView;
    private LinearLayout addBtn;
    private RecyclerAdapterMyFamilyMembers adapterMyFamilyMembers;
    private DialogFragmentCreateMemberOfFamily fragmentCreateMemberOfFamily;
    private LinearLayout family_list_container_LL;
    private LinearLayout confirm_phone_container_LL;
    private Button send_sms_btn;
    private EditText userPhone_ED;

    private List<DocumentMyFamily>  myFamilyMemberses =  new  ArrayList();
    public void upDateData(ResponseMyFamilyMembers data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
        }
    }

    public void setDataToViews() {

        if (mainData != null && isViewCreated && responseGetUserData.getConfirmedPhone()!=null) {
            family_list_container_LL.setVisibility(View.VISIBLE);
            confirm_phone_container_LL.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);



            for(int i =0;i<mainData.getDocuments().size();i++){


                if(mainData.getDocuments().get(i).getParametersCount()==0){
                    DocumentMyFamily documentMyFamily = new DocumentMyFamily();
                    documentMyFamily.setId(mainData.getDocuments().get(i).getId());
                    documentMyFamily.setName(mainData.getDocuments().get(i).getName());
                    documentMyFamily.setParametersCount(mainData.getDocuments().get(i).getParametersCount());
                    documentMyFamily.setRecordsCount(mainData.getDocuments().get(i).getRecordsCount());
                    documentMyFamily.setSharedWith(mainData.getDocuments().get(i).getSharedWith());
                    documentMyFamily.setCreateDate(mainData.getDocuments().get(i).getCreateDate());
                    documentMyFamily.setUpdateDate(mainData.getDocuments().get(i).getUpdateDate());
                    myFamilyMemberses.add(documentMyFamily);


                }



            }

            adapterMyFamilyMembers = new RecyclerAdapterMyFamilyMembers(myFamilyMemberses, getActivity(),this);
            mRecyclerView.setAdapter(adapterMyFamilyMembers);
        }
        else if (mainData != null && isViewCreated&&responseGetUserData.getConfirmedPhone()==null){
            family_list_container_LL.setVisibility(View.VISIBLE);
            confirm_phone_container_LL.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);

            for(int i =0;i<mainData.getDocuments().size();i++){


                if(mainData.getDocuments().get(i).getParametersCount()==0){
                    DocumentMyFamily documentMyFamily = new DocumentMyFamily();
                    documentMyFamily.setId(mainData.getDocuments().get(i).getId());
                    documentMyFamily.setName(mainData.getDocuments().get(i).getName());
                    documentMyFamily.setParametersCount(mainData.getDocuments().get(i).getParametersCount());
                    documentMyFamily.setRecordsCount(mainData.getDocuments().get(i).getRecordsCount());
                    documentMyFamily.setSharedWith(mainData.getDocuments().get(i).getSharedWith());
                    documentMyFamily.setCreateDate(mainData.getDocuments().get(i).getCreateDate());
                    documentMyFamily.setUpdateDate(mainData.getDocuments().get(i).getUpdateDate());
                    myFamilyMemberses.add(documentMyFamily);


                }



            }


            adapterMyFamilyMembers = new RecyclerAdapterMyFamilyMembers(myFamilyMemberses, getActivity(),this);
            mRecyclerView.setAdapter(adapterMyFamilyMembers);


            send_sms_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userPhone = userPhone_ED.getText().toString();
                    if( !userPhone.isEmpty()) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("phone", userPhone);

                        Call<String> call =
                                App.getApi().setPhone("application/json",
                                        GlobalVariables.user_auth_token,
                                        GlobalVariables.user_id,
                                        jsonObject);
                        //TODO : MOdel
                        call.enqueue(new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String resp = response.body();
                                if(resp!=null){
                                    if(resp.toLowerCase().contains("ok")){
                                        String test ="";
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                String test = "ee0";
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_user_info_my_family, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_my_family);
        addBtn = (LinearLayout) view.findViewById(R.id.addMemberOfFimilyBTN);
        family_list_container_LL = (LinearLayout) view.findViewById(R.id.family_list_container_LL);
        confirm_phone_container_LL = (LinearLayout) view.findViewById(R.id.confirm_phone_container_LL);
        send_sms_btn = (Button) view.findViewById(R.id.send_sms_btn);
        userPhone_ED = (EditText) view.findViewById(R.id.userPhone_ED);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                FragmentManager fm = getChildFragmentManager();
//                fragmentCreateMemberOfFamily = new DialogFragmentCreateMemberOfFamily();
//                fragmentCreateMemberOfFamily.show(fm, "fragmentCreateMemberOfFamily");
//                fm.executePendingTransactions();
//                fragmentCreateMemberOfFamily.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        //do whatever you want when dialog is dismissed
////                        MainController.showToast(getActivity(),"DISMISS");
//                        seeUserFamilyMembers();
//                    }
//                });
                FragmentManager fm = getChildFragmentManager();
                fragmentCreateMemberOfFamily = DialogFragmentCreateMemberOfFamily.newInstance(fm,null,DialogFragmentCreateMemberOfFamily.TYPE_NEW);
                fragmentCreateMemberOfFamily.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //do whatever you want when dialog is dismissed
//                        MainController.showToast(getActivity(),"DISMISS");
                        seeUserFamilyMembers();
                    }
                });
            }
        });
        isViewCreated = true;


        setDataToViews();

    }


    @Override
    public <T> void onItemClick(View v, T data) {
        if(data instanceof DocumentMyFamily){
            Intent familyUserInfo = new Intent( getActivity() , FileActivity.class);
            familyUserInfo.putExtra(FileActivity.DATA_KEY, new Gson().toJson(data));
            startActivity(familyUserInfo);
        }
    }
}
