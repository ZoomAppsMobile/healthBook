package com.example.d.healthbook.DialogFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.d.healthbook.Fragments.BaseDialogFragment;
import com.example.d.healthbook.R;

/**
 * Created by User on 28.08.2017.
 */

public class EditChatDialogFragment extends BaseDialogFragment implements View.OnClickListener{
    @Override
    public void onClick(View view) {

    }
    EditText startDate_ED;
    EditText day_duration_ED;
    EditText loop_duration_ED;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_woman_calendar,null);
        removeTitle();

        startDate_ED = (EditText)v.findViewById(R.id.startDate_ED);
        day_duration_ED = (EditText)v.findViewById(R.id.day_duration_ED);
        loop_duration_ED = (EditText)v.findViewById(R.id.loop_duration_ED);
        v.findViewById(R.id.clickBtnSaveEvent).setOnClickListener(this);
        v.findViewById(R.id.clickBtnCancelEvent).setOnClickListener(this);
        return v;
    }
}
