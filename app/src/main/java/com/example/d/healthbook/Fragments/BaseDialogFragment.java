package com.example.d.healthbook.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.d.healthbook.Controller.MainController;

/**
 * Created by User on 14.08.2017.
 */

public class BaseDialogFragment extends DialogFragment {
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout((int) (MainController.getScreenWidth(getActivity())*.9), ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void removeTitle(){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
