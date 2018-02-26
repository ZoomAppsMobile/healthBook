package com.example.d.healthbook.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Activities.DatePicker;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.ResponseNoteType1;
import com.example.d.healthbook.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by D on 29.06.2017.
 */

public class DialogFragmentCalendarNote extends BaseDialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    private String editTextTitleStr = "";
    private String editTextDescriptionStr = "";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private String date;
    private ResponseNoteType1 registeredUser;
    private TextView note_date_TV;
    LinearLayout linearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString("date");
        }

        Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();



    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_fragment_calendar, null);
        v.findViewById(R.id.clickBtnSaveEvent).setOnClickListener(this);
        v.findViewById(R.id.clickBtnCancelEvent).setOnClickListener(this);
        note_date_TV=(TextView ) v.findViewById(R.id.note_date_TV);
        editTextTitle = (EditText) v.findViewById(R.id.titleETcalendar);
        editTextDescription = (EditText) v.findViewById(R.id.descriptionETcalendar);
        linearLayout = (LinearLayout) v.findViewById(R.id.selectDate);
        removeTitle();
        setData();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePicker(note_date_TV);

                dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        return v;

    }

    void setData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSelected = null;
        try {
            dateSelected = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dateSelected!=null){
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String stringSelectedDay = formatter.format(dateSelected.getTime());
            note_date_TV.setText(stringSelectedDay);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clickBtnSaveEvent: {
                if(note_date_TV.getText().toString().equals("00.00.00")){

                    DialogFragment dateDialog = new DatePicker(note_date_TV);

                    dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
                }
                else {

                    Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    //TODO: set TIme to date
                    editTextTitleStr = editTextTitle.getText().toString();
                    editTextDescriptionStr = editTextDescription.getText().toString();
                    if (editTextTitleStr.equals("") && editTextDescriptionStr.equals("")) {
                        dismiss();
                    } else {


                        saveNoteType1("1", editTextTitleStr, editTextDescriptionStr,note_date_TV.getText().toString() );
                    }

                }



                break;
            }
            case R.id.clickBtnCancelEvent:{
                dismiss();
            }

        }


    }

    public void saveNoteType1(String type, String title, String text, String date) {
        //create Model
        ((CalendarActivity)getActivity()).sendNewEvent(type,title,text,date);
        dismiss();
    }


    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }


}
