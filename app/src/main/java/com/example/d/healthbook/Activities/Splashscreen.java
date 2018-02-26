package com.example.d.healthbook.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;


import com.example.d.healthbook.Bottom_helper.SharedPreference;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.R;

import java.util.List;
import java.util.Locale;



public class Splashscreen extends Activity   {






    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if(SharedPreference.getToken(Splashscreen.this).equals("")){

                    Intent intent= new Intent(Splashscreen.this,AuthActivity.class);
                    startActivity(intent);

                }else {

                    Intent intent= new Intent(Splashscreen.this,UserActivityInfo.class);
                    GlobalVariables.user_auth_token = SharedPreference.getToken(Splashscreen.this);
                    GlobalVariables.user_id =Integer.valueOf(SharedPreference.getId(Splashscreen.this));
                    startActivity(intent);

                }




            }
        }, 1000);











    }









}