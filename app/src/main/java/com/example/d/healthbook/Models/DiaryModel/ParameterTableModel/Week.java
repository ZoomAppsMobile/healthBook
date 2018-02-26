package com.example.d.healthbook.Models.DiaryModel.ParameterTableModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 26.09.2017.
 */

public class Week {
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("parameters")
    @Expose
    private List<Parameter> parameters = null;
    @SerializedName("days")
    @Expose
    private List<Day> days = null;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
