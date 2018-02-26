package com.example.d.healthbook.UI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.d.healthbook.Fragments.BaseDialogFragment;
import com.example.d.healthbook.R;

/**
 * Created by User on 17.08.2017.
 */

public class CustomDialog extends BaseDialogFragment {

    final String LOG_TAG = "myLogs";
    private String newsFeedSt = "";
    private String statusSt = "";
    private CheckBox news_chBox_Feed;
    private CheckBox status_chBox_Feed;

    private View.OnClickListener Positive_ClickListener;
    private View.OnClickListener Negative_ClickListener;
    private View.OnClickListener Default_ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };
    private  static final String EXTRA_TITLE = "Title";
    private  static final String EXTRA_MESSAGE = "Message";

    public static CustomDialog createInstance(String title,String message) {
        CustomDialog fragment = new CustomDialog();
        Bundle bundle = new Bundle(2);
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment ;
    }

    public void setPositive_ClickListener(View.OnClickListener positive_ClickListener) {
        this.Positive_ClickListener = positive_ClickListener;
    }
    public void setNegative_ClickListener(View.OnClickListener negative_clickListener){
        this.Negative_ClickListener = negative_clickListener;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        removeTitle();
        View v = inflater.inflate(R.layout.dialog_custom_view, null);

        v.findViewById(R.id.clickBtnOkEvent).setOnClickListener(Default_ClickListener);
        v.findViewById(R.id.clickBtnCancelEvent).setOnClickListener(Default_ClickListener);

        if(Positive_ClickListener!=null){
            View.OnClickListener positive = new CustomClick(Positive_ClickListener);
            v.findViewById(R.id.clickBtnOkEvent).setOnClickListener(positive);
        }
        else if(Negative_ClickListener == null) {
            v.findViewById(R.id.clickBtnCancelEvent).setVisibility(View.INVISIBLE);
        }
        if(Negative_ClickListener!=null){
            View.OnClickListener negative = new CustomClick(Negative_ClickListener);
            v.findViewById(R.id.clickBtnCancelEvent).setOnClickListener(negative);
        }
//        else{
//            v.findViewById(R.id.clickBtnOkEvent).setOnClickListener(Negative_ClickListener);
//        }


        ((TextView)v.findViewById(R.id.title_TV)).setText(getArguments().getString(EXTRA_TITLE));
        ((TextView)v.findViewById(R.id.message_TV)).setText(getArguments().getString(EXTRA_MESSAGE));

        return v;
    }

    public class CustomClick implements View.OnClickListener{
        View.OnClickListener baseClickListener;
        public CustomClick(View.OnClickListener _clickListener){
            baseClickListener = _clickListener;
        }
        @Override
        public void onClick(View view) {
            baseClickListener.onClick(view);
            dismiss();
        }
    }
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}