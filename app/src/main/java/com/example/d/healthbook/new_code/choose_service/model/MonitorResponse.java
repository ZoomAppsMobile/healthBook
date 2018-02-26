package com.example.d.healthbook.new_code.choose_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonitorResponse {

@SerializedName("success")
@Expose
private Boolean success;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

}