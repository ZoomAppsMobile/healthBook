package com.example.d.healthbook.Activities;

import android.Manifest;
import android.accounts.AccountAuthenticatorActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Bottom_helper.SharedPreference;
import com.example.d.healthbook.Controller.SettingsController;
import com.example.d.healthbook.R;
import com.example.d.healthbook.DialogFragments.ChangePasswordDialogFragment;
import com.example.d.healthbook.UI.CustomDialogBuilder;
import com.example.d.healthbook.UI.MyToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener ,View.OnClickListener {


    private MyToolbar toolbar;
    @BindView(R.id.changePassword_setting_RL)
    RelativeLayout changePassword_setting_RL;

    @BindView(R.id.notification_setting_RL)
    RelativeLayout notification_setting_RL;

    @BindView(R.id.notification_setting_S)
    Switch notification_setting_S;

    @BindView(R.id.feedDev_setting_RL)
    RelativeLayout feedDev_setting_RL;

    @BindView(R.id.about_setting_RL)
    RelativeLayout about_setting_RL;

    @BindView(R.id.exit_setting_RL)
    RelativeLayout exit_setting_RL;

    ChangePasswordDialogFragment dialogFragmentChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);


        if(SharedPreference.getPushDiscountsState(this).equals("1")){
            notification_setting_S.setChecked(false);
        }
        else {
            notification_setting_S.setChecked(true);

        }


        notification_setting_S.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreference.setPushDiscountsState(SettingsActivity.this,"");


                } else {
                    SharedPreference.setPushDiscountsState(SettingsActivity.this,"1");


                }
            }
        });









        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        App.getInstance().setCurrentContext(this);
        setValues();
    }

    void setValues(){
        //notification_setting_S.setChecked(SettingsController.getInstance().getNotificationStatus());
       // notification_setting_S.setOnCheckedChangeListener(this);

        changePassword_setting_RL.setOnClickListener(this);
        about_setting_RL.setOnClickListener(this);
        feedDev_setting_RL.setOnClickListener(this);
        exit_setting_RL.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                closeSettings();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeSettings();
        //super.onBackPressed();
    }

    void closeSettings(){
        startActivity(new Intent(SettingsActivity.this,UserActivityInfo.class));
        finish();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        SettingsController.getInstance().changeNotifiactionStatus(b);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.changePassword_setting_RL:{
                dialogFragmentChangePassword = new ChangePasswordDialogFragment();
                dialogFragmentChangePassword.show(getSupportFragmentManager(),"dialogFragmentChangePassword");
                break;
            }
            case R.id.about_setting_RL:{
                CustomDialogBuilder builder = new CustomDialogBuilder(SettingsActivity.this).setTitle("О приложении").setMessage("Lorem Ipsum").build();
                builder.showDialog();
                break;
            }
            case R.id.feedDev_setting_RL:{
                CustomDialogBuilder builder =
                        new CustomDialogBuilder(SettingsActivity.this)
                                .setTitle("Сообщить о проблеме")
                                .setMessage("Данная функция скоро появится").build();
                builder.showDialog();
                break;
            }
            case R.id.exit_setting_RL:{





                String message = "Вы действительно хотите выйти из аккаунта?";
                String button1String = "Ok";
                String button2String = "Отменить";

                AlertDialog.Builder ad = new AlertDialog.Builder(SettingsActivity.this);
                ad.setMessage(message); // сообщение
                ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        Intent i = new Intent(SettingsActivity.this,AuthActivity.class);
                        SharedPreference.clearUserName(SettingsActivity.this);
                        startActivity(i);
                    }
                });
                ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                    }
                });

                ad.show();
            ;

                break;
            }
        }
    }
}
