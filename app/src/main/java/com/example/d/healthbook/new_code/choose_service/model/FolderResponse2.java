package com.example.d.healthbook.new_code.choose_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolderResponse2 {

@SerializedName("diary_id")
@Expose
private String diaryId;
@SerializedName("parameter_type_id")
@Expose
private Integer parameterTypeId;

@SerializedName("value")
@Expose
private Integer value;
@SerializedName("create_date")
@Expose
private String createDate;
@SerializedName("id")
@Expose
private String id;

public String getDiaryId() {
return diaryId;
}

public void setDiaryId(String diaryId) {
this.diaryId = diaryId;
}

public Integer getParameterTypeId() {
return parameterTypeId;
}

public void setParameterTypeId(Integer parameterTypeId) {
this.parameterTypeId = parameterTypeId;
}



public Integer getValue() {
return value;
}

public void setValue(Integer value) {
this.value = value;
}

public String getCreateDate() {
return createDate;
}

public void setCreateDate(String createDate) {
this.createDate = createDate;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

}