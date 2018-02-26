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

import com.example.d.healthbook.Activities.ClinicActivityInfo;
import com.example.d.healthbook.Adapters.RecyclerAdapterMentionsClinicInfo;
import com.example.d.healthbook.Adapters.RecyclerDoctorListAdapter;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by D on 09.06.2017.
 */

public class TabFragmentMentionsClinic extends Fragment {

    private RecyclerView recycler_mention_clinic;
    private boolean isViewCreated;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterMentionsClinicInfo recyclerAdapterMentionsClinicInfo;
    @BindView((R.id.fabClinic))
    FloatingActionButton fab;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_fragment_mention_clinic, container, false);
        ButterKnife.bind(this,v);

        return v;


    }
    @OnClick(R.id.fabClinic)
    public  void  ggg(){


        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Вы хотите оставить озыв?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {

//                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());


                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recycler_mention_clinic = (RecyclerView) view.findViewById(R.id.recycler_mention_clinic);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_mention_clinic.setLayoutManager(mLayoutManager);
        recyclerAdapterMentionsClinicInfo = new RecyclerAdapterMentionsClinicInfo(GlobalVariables.mentions, getActivity());
        recycler_mention_clinic.setAdapter(recyclerAdapterMentionsClinicInfo);

        isViewCreated = true;
//        setDataToViews();

    }
}
