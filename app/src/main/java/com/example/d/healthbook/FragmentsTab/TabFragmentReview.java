package com.example.d.healthbook.FragmentsTab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Adapters.RecyclerAdapterMentionsClinicInfo;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.MentionsModel;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by D on 02.06.2017.
 */

public class TabFragmentReview extends Fragment {
    ResponseDoctorInfo mainData;
    FloatingActionButton fabClinic;
    //    private boolean isViewCreated = false;
//    private TextView textView;
//
//    public void upDateData(ResponseDoctorInfo data) {
//        if (data != null) {
//            mainData = data;
//            setDataToViews();
//        }
//    }
//
//    public void setDataToViews() {
//        if (mainData != null && isViewCreated)
//            textView.setText(mainData.getInfoDs());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.tab_fragment_review, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        textView = (TextView) view.findViewById(R.id.pagerReviewTV);
//        isViewCreated = true;
//        setDataToViews();
//
//    }
    private RecyclerView recycler_mention_clinic;
    private boolean isViewCreated;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterMentionsClinicInfo recyclerAdapterMentionsClinicInfo;
    String doctorId;

    public void upDateData(ResponseDoctorInfo data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
        }
    }

    public void setDataToViews() {
//        if (mainData != null && isViewCreated)
//            textView.setText(mainData.getInfoDs());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_mention_clinic.setLayoutManager(mLayoutManager);
        //recyclerAdapterMentionsClinicInfo = new RecyclerAdapterMentionsClinicInfo(mainData.get.mentions, getActivity());
        recycler_mention_clinic.setAdapter(recyclerAdapterMentionsClinicInfo);

        isViewCreated = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  View view = inflater.inflate(R.layout.tab_fragment_mention_clinic, container, false);

        // ButterKnife.bind(this, view);

        return inflater.inflate(R.layout.tab_fragment_mention_clinic, container, false);
    }

//
//    @OnClick(R.id.fabClinic)
//    public void ggg() {
//
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage("Вы хотите оставить озыв?")
//                .setCancelable(false)
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, final int id) {
//
////                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String currentDateandTime = sdf.format(new Date());
//
//
//                    }
//                })
//                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, final int id) {
//                        dialog.cancel();
//
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recycler_mention_clinic = (RecyclerView) view.findViewById(R.id.recycler_mention_clinic);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            doctorId = bundle.getString("doctorId", "");
            Toast.makeText(getContext(), doctorId, Toast.LENGTH_SHORT).show();
            App.getApi().getMentionExspert(doctorId, GlobalVariables.user_auth_token).enqueue(new Callback<MentionsModel>() {
                @Override
                public void onResponse(Call<MentionsModel> call, Response<MentionsModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MentionsModel mentionsModel = response.body();
                            recyclerAdapterMentionsClinicInfo = new RecyclerAdapterMentionsClinicInfo(mentionsModel, getActivity());
                            recycler_mention_clinic.setAdapter(recyclerAdapterMentionsClinicInfo);
                        }
                    } else
                        Toast.makeText(getContext(), "Ошибка загрузки отзывов", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<MentionsModel> call, Throwable throwable) {

                }
            });
        }
        setDataToViews();
    }
}