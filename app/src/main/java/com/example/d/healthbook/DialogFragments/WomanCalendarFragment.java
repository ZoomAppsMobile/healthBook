package com.example.d.healthbook.DialogFragments;

import android.app.backup.SharedPreferencesBackupHelper;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Activities.DatePicker;
import com.example.d.healthbook.Activities.DatePicker2;
import com.example.d.healthbook.Bottom_helper.SharedPreference;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.CalendarBekarysa.womancycle.WomanCycle;
import com.example.d.healthbook.CalendarBekarysa.womancycle.adapter.MyAdapter;
import com.example.d.healthbook.Fragments.BaseDialogFragment;
import com.example.d.healthbook.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by User on 14.08.2017.
 */

public class WomanCalendarFragment extends BaseDialogFragment implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }
    TextView startDate_ED;
    EditText day_duration_ED;
    EditText loop_duration_ED;
    Button cancleBtn;
    Button okBtn;
    protected RecyclerView recyclerView;
    private MyAdapter mNewsAdapter;
    List<WomanCycle> objectList=new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_woman_calendar,null);
        removeTitle();


        recyclerView = (RecyclerView) v.findViewById(R.id.cake_list);


        objectList=(ArrayList<WomanCycle>) getArguments().getSerializable("lstContact");


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        mNewsAdapter = new MyAdapter(getContext(),objectList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mNewsAdapter);







        cancleBtn = (Button) v.findViewById(R.id.clickBtnCancelEvent);
        okBtn = (Button) v.findViewById(R.id.clickBtnSaveEvent);


        loop_duration_ED = (EditText)v.findViewById(R.id.loop_duration_ED);
        day_duration_ED = (EditText)v.findViewById(R.id.day_duration_ED);

        startDate_ED = (TextView) v.findViewById(R.id.startDate_ED);
        v.findViewById(R.id.clickBtnSaveEvent).setOnClickListener(this);
        v.findViewById(R.id.clickBtnCancelEvent).setOnClickListener(this);

        loop_duration_ED.setText(SharedPreference.getName(getContext()));
        day_duration_ED.setText(SharedPreference.getNumber(getContext()));
        startDate_ED.setText(SharedPreference.getCateory(getContext()));


        startDate_ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePicker2(startDate_ED);
                dateDialog.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(startDate_ED.getText().toString().length()>0 & day_duration_ED.getText().toString().length()>0 & loop_duration_ED.getText().toString().length()>0 ){

                    SharedPreference.setName(getContext(),loop_duration_ED.getText().toString());
                    SharedPreference.setNumber(getContext(),day_duration_ED.getText().toString());
                    SharedPreference.setCateory(getContext(),startDate_ED.getText().toString());
                    dismiss();

                    saveTakingDrugs(startDate_ED.getText().toString().trim()+" 00:00:00",Integer.valueOf(loop_duration_ED.getText().toString().trim()), Integer.valueOf(day_duration_ED.getText().toString().trim()));

                   // saveTakingDrugs("2022-04-19 00:00:00", 25,2);
                    Toast.makeText(getContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Заполните обязательные поля", Toast.LENGTH_SHORT).show();

                }


            }
        });



        return v;
    }

    public void saveTakingDrugs(String dateBegin,Integer duration,Integer durationPeriod) {
        ((CalendarActivity) getActivity()).sendNewTakingWoman(dateBegin,duration,durationPeriod);
        dismiss();
    }

}
