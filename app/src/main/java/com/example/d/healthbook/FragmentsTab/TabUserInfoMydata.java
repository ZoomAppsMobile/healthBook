package com.example.d.healthbook.FragmentsTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.d.healthbook.GlobalVariables.GlobalVariables.responseGetUserData;

/**
 * Created by D on 01.07.2017.
 */

public class TabUserInfoMydata extends Fragment {


    @BindView(R.id.txtHeight)
    TextView txtHeight;
    @BindView(R.id.txtWeight)
    TextView txtWeight;
    Unbinder unbinder;
    @BindView(R.id.txtNumber)
    TextView txtNumber;
    @BindView(R.id.txtYear)
    TextView txtYear;
    @BindView(R.id.allergy)
    TextView allergy;
    @BindView(R.id.pastIlnessesProfileEdit)
    TextView pastIlnessesProfileEdit;
    @BindView(R.id.chronicDiseasesProfileEdit)
    TextView chronicDiseasesProfileEdit;

    @BindView(R.id.heredityProfileEdit)
    TextView heredityProfileEdit;

    @BindView(R.id.badhabitsProfileEdit)
    TextView badhabitsProfileEdit;


    @BindView(R.id.txtBloodGrooup)
    TextView txtBloodGroup;



    private TextView userGender;
    private TextView birthdateUser;
    private boolean isViewCreated = false;
    private ResponseGetUserData mainData;

    public void upDateData(ResponseGetUserData data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
        }
    }

    public void setDataToViews() {
        if (mainData != null && isViewCreated) {
            userGender.setText(mainData.getGender());
            birthdateUser.setText(mainData.getBirthday());
            txtHeight.setText(String.valueOf(mainData.getHeight()));
            txtWeight.setText(String.valueOf(mainData.getWeight()));
            txtNumber.setText(String.valueOf(responseGetUserData.getHomePhone()));
            txtYear.setText(mainData.getBirthday());
            allergy.setText(mainData.getAllergy());
            pastIlnessesProfileEdit.setText(mainData.getPastIllnesses());
            chronicDiseasesProfileEdit.setText(mainData.getChronicDiseases());


            heredityProfileEdit.setText(String.valueOf(mainData.getHeredity()));
            badhabitsProfileEdit.setText(String.valueOf(mainData.getBadHabits()));
            txtBloodGroup.setText(String.valueOf(mainData.getBloodGroup()));


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_user_info_my_date, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        userGender = (TextView) view.findViewById(R.id.userGender);
        birthdateUser = (TextView) view.findViewById(R.id.birthdateUser);
        isViewCreated = true;
        setDataToViews();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
