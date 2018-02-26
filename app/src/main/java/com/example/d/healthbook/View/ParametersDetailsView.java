package com.example.d.healthbook.View;

import com.example.d.healthbook.Adapters.BaseAdapterListener;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Parameter;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Week;

import java.util.List;

/**
 * Created by User on 27.09.2017.
 */

public interface ParametersDetailsView extends BaseAdapterListener<Parameter> {
    void onDataReady(Week datum);
    void onShowToast(String message);
}
