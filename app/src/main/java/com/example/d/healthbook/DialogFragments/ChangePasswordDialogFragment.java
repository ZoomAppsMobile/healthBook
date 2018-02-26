package com.example.d.healthbook.DialogFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.d.healthbook.Fragments.BaseDialogFragment;
import com.example.d.healthbook.R;

/**
 * Created by User on 17.08.2017.
 */

public class ChangePasswordDialogFragment extends BaseDialogFragment {

    final String LOG_TAG = "myLogs";
    private String newsFeedSt = "";
    private String statusSt = "";
    private CheckBox news_chBox_Feed;
    private CheckBox status_chBox_Feed;

    private View.OnClickListener Positive_ClickListener;
    private  static final String EXTRA_MESSAGE = "Message";
    public static ChangePasswordDialogFragment newInstance(String message , View.OnClickListener Positive_ClickListener) {
        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        Bundle bundle = new Bundle(2);
        bundle.putString(EXTRA_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment ;
    }

    public void setPositive_ClickListener(View.OnClickListener positive_ClickListener) {
        this.Positive_ClickListener = positive_ClickListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        removeTitle();
        View v = inflater.inflate(R.layout.dialog_change_password, null);
        v.findViewById(R.id.clickBtnCancelEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        v.findViewById(R.id.clickBtnOkEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }


    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}