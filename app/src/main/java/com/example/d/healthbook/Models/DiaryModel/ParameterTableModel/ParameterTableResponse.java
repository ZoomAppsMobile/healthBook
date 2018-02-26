package com.example.d.healthbook.Models.DiaryModel.ParameterTableModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 26.09.2017.
 */

public class ParameterTableResponse {
    @SerializedName("week")
    @Expose
    private List<Week> week = null;

    public List<Week> getWeek() {
        return week;
    }

    public void setWeek(List<Week> week) {
        this.week = week;
    }
}
