
package com.example.d.healthbook.Models.DiaryModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParametersResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("beginGoal")
    @Expose
    private Object beginGoal;
    @SerializedName("endGoal")
    @Expose
    private Object endGoal;
    @SerializedName("minGoal")
    @Expose
    private Object minGoal;
    @SerializedName("maxGoal")
    @Expose
    private Object maxGoal;
    @SerializedName("periodGoal")
    @Expose
    private Object periodGoal;
    @SerializedName("diary_id")
    @Expose
    private String diaryId;
    @SerializedName("parameter_type")
    @Expose
    private ParameterType parameterType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getBeginGoal() {
        return beginGoal;
    }

    public void setBeginGoal(Object beginGoal) {
        this.beginGoal = beginGoal;
    }

    public Object getEndGoal() {
        return endGoal;
    }

    public void setEndGoal(Object endGoal) {
        this.endGoal = endGoal;
    }

    public Object getMinGoal() {
        return minGoal;
    }

    public void setMinGoal(Object minGoal) {
        this.minGoal = minGoal;
    }

    public Object getMaxGoal() {
        return maxGoal;
    }

    public void setMaxGoal(Object maxGoal) {
        this.maxGoal = maxGoal;
    }

    public Object getPeriodGoal() {
        return periodGoal;
    }

    public void setPeriodGoal(Object periodGoal) {
        this.periodGoal = periodGoal;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }
}
