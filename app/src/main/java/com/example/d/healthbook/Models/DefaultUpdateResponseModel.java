
package com.example.d.healthbook.Models;

import com.google.gson.annotations.SerializedName;

public class DefaultUpdateResponseModel {

    @SerializedName("success")
    private Boolean mSuccess;

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

}
