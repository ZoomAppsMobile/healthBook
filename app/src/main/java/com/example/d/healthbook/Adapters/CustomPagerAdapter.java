package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.d.healthbook.Fragments.SearchDoctorsFragment;
import com.example.d.healthbook.Fragments.See_Clinic_List_Fragment;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.View.ViewPagerInterface;

/**
 * Created by D on 07.06.2017.
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private Context mContext;
    private String titleClinic, cityName, speciality;
    private Boolean checkGetData = false;

    public CustomPagerAdapter(Context context, FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.mContext = context;
    }

    public CustomPagerAdapter(Context context, FragmentManager fm, int NumOfTabs, String titleClinic, String cityName, String speciality, Boolean checkGetData) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.mContext = context;
        this.titleClinic = titleClinic;
        this.cityName = cityName;
        this.speciality = speciality;
        this.checkGetData = checkGetData;
    }

    SearchDoctorsFragment tab1;
    See_Clinic_List_Fragment tab2;

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (checkGetData) {
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalVariables.titleClinic_key, titleClinic);
                    bundle.putString(GlobalVariables.cityName_key, cityName);
                    bundle.putString(GlobalVariables.speciality_key, speciality);
                    tab1 = new SearchDoctorsFragment();
                    tab1.setArguments(bundle);
                } else {
                    tab1 = new SearchDoctorsFragment();
                }

                return tab1;
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString(GlobalVariables.titleClinic_key, titleClinic);
                bundle.putString(GlobalVariables.cityName_key, cityName);
                bundle.putString(GlobalVariables.speciality_key, speciality);
                tab2 = new See_Clinic_List_Fragment();
                tab2.setArguments(bundle);

                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
