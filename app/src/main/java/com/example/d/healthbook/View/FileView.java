package com.example.d.healthbook.View;

import com.example.d.healthbook.Adapters.BaseAdapterListener;
import com.example.d.healthbook.Models.DiaryModel.CompleteDiaryData;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Week;
import com.example.d.healthbook.Models.DiaryModel.ParametersResponse;
import com.example.d.healthbook.Models.DocumentMyFamily;

import java.util.List;

/**
 * Created by User on 19.09.2017.
 */

public interface FileView extends BaseAdapterListener<Week>{
    void onSetViewData(DocumentMyFamily data);
    void onFullDataLoaded(CompleteDiaryData full_data);
    void onParametersLoaded(ParametersResponse data);
    void onEditClick();
    void onShowMessage(String message);
    void onFatalError();

    void onTableDataLoaded(List<Week> datum);
}
