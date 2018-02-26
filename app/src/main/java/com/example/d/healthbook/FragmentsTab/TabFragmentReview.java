package com.example.d.healthbook.FragmentsTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Adapters.RecyclerAdapterMentionsClinicInfo;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;

/**
 * Created by D on 02.06.2017.
 */

public class TabFragmentReview extends Fragment {
   ResponseDoctorInfo mainData;
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
        return inflater.inflate(R.layout.tab_fragment_mention_clinic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recycler_mention_clinic = (RecyclerView) view.findViewById(R.id.recycler_mention_clinic);
        setDataToViews();
    }
}