package com.example.d.healthbook.Models.DiaryModel.ParameterTableModel;

/**
 * Created by User on 26.09.2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("parameters")
    @Expose
    private List<Parameter__> parameters = null;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Parameter__> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter__> parameters) {
        this.parameters = parameters;
    }

}