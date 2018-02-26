package com.example.d.healthbook.Fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.d.healthbook.Activities.DatePicker2;
import com.example.d.healthbook.Adapters.CustomMyAdapterSpinner;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Models.ResponseNoteType1;
import com.example.d.healthbook.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by D on 13.07.2017.
 */

public class DialogFragmentAddDrugs extends BaseDialogFragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Unbinder unbinder;
    private String editTextquantityDrugsETStr = "";
    private Integer editTextperiodETStr = 0;
    private Integer editTextdurationETStr = 0;
    private String editTextTitleStr = "";
    private String editTextDescriptionStr = "";
    private String admissiontimeETcalendarStr = "";
    private String admissiontimeETcalendar2Str = "";




    private String date;
    private ResponseNoteType1 registeredUser;
    private CustomMyAdapterSpinner adapter;

    TextView startDate_ED;
    private EditText editTextTitle;
    private EditText editTextDescription;

    private TextView admissiontimeETcalendar;


    private EditText edquantityDrugs;
    private EditText edperiodET;
    private EditText eddurationET;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString("date");
        }


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_fragment_add_drugs, null);

        v.findViewById(R.id.clickBtnSaveEvent).setOnClickListener(this);

        editTextTitle = (EditText) v.findViewById(R.id.titleETcalendar);
        editTextDescription = (EditText) v.findViewById(R.id.descriptionETcalendar);
        edquantityDrugs = (EditText) v.findViewById(R.id.quantityDrugsSpinner);
        startDate_ED = (TextView) v.findViewById(R.id.startDate_ED);
        //startDate_ED.setText(date);

        admissiontimeETcalendar = (TextView) v.findViewById(R.id.admissiontimeETcalendar);
        admissiontimeETcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });



        startDate_ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePicker2(startDate_ED);
                dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });



        edperiodET = (EditText) v.findViewById(R.id.periodSpinner);


        eddurationET = (EditText) v.findViewById(R.id.durationSpinner);


        removeTitle();

        unbinder = ButterKnife.bind(this, v);
        return v;

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clickBtnSaveEvent: {


                if(editTextDescription.getText().toString().length()>0 & editTextTitle.getText().toString().length()>0& edquantityDrugs.getText().toString().length()>0&
                        edperiodET.getText().toString().length()>0&
                        eddurationET.getText().toString().length()>0 & !startDate_ED.getText().toString().equals("Дата начала")


                        ){



                    Bundle bundle = new Bundle();
                    editTextTitleStr = editTextTitle.getText().toString();
                    editTextDescriptionStr = editTextDescription.getText().toString();

                    admissiontimeETcalendarStr = admissiontimeETcalendar.getText().toString();
//
                    List<String> times = new ArrayList<>();
                    times.add(admissiontimeETcalendarStr);
                   // times.add(admissiontimeETcalendar2Str);

                    editTextquantityDrugsETStr = edquantityDrugs.getText().toString();
                    editTextperiodETStr = Integer.valueOf(edperiodET.getText().toString().trim());
                    editTextdurationETStr = Integer.valueOf(eddurationET.getText().toString().trim());

                    if (editTextTitleStr.equals("") && editTextDescriptionStr.equals("")) {
                        dismiss();
                    } else {

                        saveTakingDrugs("2", editTextTitleStr, editTextDescriptionStr, startDate_ED.getText().toString(),
                                editTextquantityDrugsETStr, editTextperiodETStr, editTextdurationETStr, times);


                    }

                }
                else {
                    Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }



                break;
            }

        }


    }
    public  void  timePicker(){




        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    admissiontimeETcalendar.setText( String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Выберите время");
        mTimePicker.show();






    }
    public void saveTakingDrugs(String type, String title, String text, String date, String quantityDrugs,
                                Integer period, Integer duration, List<String> times) {
        ((CalendarActivity) getActivity()).sendNewTakingDrugs(type, title, text, date, quantityDrugs,
                period, duration, times);
        dismiss();
    }


    public void hideKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.clickBtnCancelEvent)
    public void onViewClicked() {
        dismiss();
    }
}