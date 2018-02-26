package com.example.d.healthbook.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TextView;


import com.example.d.healthbook.R;

import java.util.Calendar;

public class DatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    TextView txtDate;



    public DatePicker(TextView txtDate) {
        this.txtDate = txtDate;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
 
        // определяем текущую дату
        final Calendar c = Calendar.getInstance();

        c.add(Calendar.DAY_OF_YEAR, 1);




        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
 
        // создаем DatePickerDialog и возвращаем его



        DatePickerDialog dpd=new DatePickerDialog(getActivity(), this,
                year, month, day);

         //dpd.getDatePicker().setMinDate(c.getTimeInMillis());

        Dialog picker = dpd;



        picker.setTitle("Выберите дату");
 
        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        // добавляем кастомный текст для кнопки
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText("Готово");
 
    }
 
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, 
            int month, int day) {

      //  timePicker(day,(month+1),year);
        txtDate.setText(day+"."+ (month+1) + "."+ year  );
    }



}