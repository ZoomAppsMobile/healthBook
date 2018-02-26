package com.example.d.healthbook.new_code.choose_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolderDatum {

@SerializedName("diary_id")
@Expose
private String diaryId;
@SerializedName("parameter_type_id")
@Expose
private String parameterTypeId;
@SerializedName("value")
@Expose
private String value;
@SerializedName("measure_date")
@Expose
private String measureDate;

public String getDiaryId() {
return diaryId;
}

public void setDiaryId(String diaryId) {
this.diaryId = diaryId;
}

public String getParameterTypeId() {
return parameterTypeId;
}

public void setParameterTypeId(String parameterTypeId) {
this.parameterTypeId = parameterTypeId;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

public String getMeasureDate() {
return measureDate;
}

public void setMeasureDate(String measureDate) {
this.measureDate = measureDate;
}

}