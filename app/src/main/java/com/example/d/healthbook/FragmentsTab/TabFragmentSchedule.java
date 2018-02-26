package com.example.d.healthbook.FragmentsTab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Controller.DateController;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.Models.Schedule;
import com.example.d.healthbook.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by D on 02.06.2017.
 */

public class TabFragmentSchedule extends Fragment {


    ResponseDoctorInfo mainData;
    private TextView textView;
    private boolean isViewCreated = false;

    public void upDateData(ResponseDoctorInfo data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
        }
    }

    public void setDataToViews() {
        if (mainData != null && isViewCreated)
            if (mainData.getSchedule().size() != 0) {
//                textView.setText(mainData.getSchedule().get(0).getDay() + ":" + mainData.getSchedule().get(0).getStartTime() + "/"
//                        + mainData.getSchedule().get(0).getFinishTime());
                textView.setText(createSpannable(mainData.getSchedule()));
            }
    }

    String parseDay(Integer day_int){
        switch (day_int){
            case 1:{
                return "Пн.";
            }
            case 2:{
                return "Вт.";
            }
            case 3:{
                return "Ср.";
            }
            case 4:{
                return "Чт.";
            }
            case 5:{
                return "Пят.";
            }
            case 6:{
                return "Сб.";
            }
            case 7:{
                return "Вс.";
            }
        }
        return "";
    }

    SpannableString createSpannable(List<Schedule> datum){
        if(datum==null || datum.isEmpty()){
            return new SpannableString("");
        }
        List<Integer[]> list = new ArrayList<Integer[]>();
        String setText = "";
        Integer last_length = 0;
        for (Schedule tmp_data:datum) {
            Integer[] tmp_index = new Integer[2];
            String startDate = DateController.convertFormat("hh:mm:ss","HH:mm",tmp_data.getStartTime());
            String endDate = DateController.convertFormat("hh:mm:ss","HH:mm",tmp_data.getFinishTime());
            String cur_text = String.format("%s - c %s до %s \n",parseDay(tmp_data.getDay()) ,startDate , endDate);


            tmp_index[0] = cur_text.indexOf('-')+last_length+2;
            tmp_index[1] = cur_text.indexOf("\n")+last_length;

            setText+= cur_text;

            last_length = setText.length();
            list.add(tmp_index);
        }

        SpannableString spannableString = new SpannableString(setText);
        for (Integer[] tmp:list) {
            ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(MainController.getColor(getActivity(),R.color.bpGreen));
            spannableString.setSpan(foregroundSpan, tmp[0], tmp[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
       // textView.setText(spannableString);
        return spannableString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_shedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.pagetSheduledocTV);
        isViewCreated = true;
        setDataToViews();

    }
}
