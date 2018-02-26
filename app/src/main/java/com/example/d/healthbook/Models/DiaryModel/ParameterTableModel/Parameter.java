package com.example.d.healthbook.Models.DiaryModel.ParameterTableModel;

/**
 * Created by User on 26.09.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameter {

    @SerializedName("parameter_type_id")
    @Expose
    private Integer parameterTypeId;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("measure_name")
    @Expose
    private String measureName;
    @SerializedName("is_reached")
    @Expose
    private Boolean isReached;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public Boolean getIsReached() {
        return isReached;
    }

    public void setIsReached(Boolean isReached) {
        this.isReached = isReached;
    }

}