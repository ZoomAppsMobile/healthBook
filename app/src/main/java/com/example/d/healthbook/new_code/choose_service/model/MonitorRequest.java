package com.example.d.healthbook.new_code.choose_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonitorRequest {

@SerializedName("diary_id")
@Expose
private String diaryId;
@SerializedName("parameter_type")
@Expose
private ParameterType parameterType;

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