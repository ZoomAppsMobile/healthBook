package com.example.d.healthbook.UI;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.d.healthbook.DialogFragments.ChangePasswordDialogFragment;
import com.example.d.healthbook.Fragments.BaseDialogFragment;
import com.example.d.healthbook.R;

/**
 * Created by User on 17.08.2017.
 */

public class CustomDialogBuilder {
    Context mContext;
    CustomDialog mCustomDialog;

    private String title;
    private String message;
    private View.OnClickListener Positive_ClickListener;
    private View.OnClickListener Negative_ClickListener;

    public CustomDialogBuilder(Context context){
        mContext = context;
    }
    public CustomDialogBuilder setTitle(String title){
        this.title = title;
        return this;
    }
    public CustomDialogBuilder setMessage(String message){
        this.message = message;
        return this;
    }


    public CustomDialogBuilder setPositiveClickListener(View.OnClickListener clickListener){
        Positive_ClickListener = clickListener;
        return this;
    }
    public CustomDialogBuilder setNegativeClickListener(View.OnClickListener clickListener){
        Negative_ClickListener = clickListener;
        return this;
    }
    public CustomDialogBuilder build(){
        mCustomDialog = CustomDialog.createInstance(title,message);
        mCustomDialog.setPositive_ClickListener(Positive_ClickListener);
        mCustomDialog.setNegative_ClickListener(Negative_ClickListener);
        return this;
    }
    public void showDialog(){
        mCustomDialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"mCustomDialog");
    }
}
