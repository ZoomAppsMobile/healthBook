package com.example.d.healthbook.Models.DiaryModel.ParameterTableModel;

/**
 * Created by User on 26.09.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameter__ {

    @SerializedName("parameter_type_id")
    @Expose
    private Integer parameterTypeId;
    @SerializedName("value")
    @Expose
    private String value;

    public Integer getParameterTypeId() {
        return parameterTypeId;
    }

    public void setParameterTypeId(Integer parameterTypeId) {
        this.parameterTypeId = parameterTypeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}