package com.example.d.healthbook.Models.DiaryModel.ParameterTableModel;

/**
 * Created by User on 26.09.2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Day {

    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("parameters")
    @Expose
    private List<Parameter_> parameters = null;
    @SerializedName("hours")
    @Expose
    private List<Hour> hours = null;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<Parameter_> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter_> parameters) {
        this.parameters = parameters;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

}