package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 31.08.2017.
 */

public class SubsriptionCheckResponseData {
    @SerializedName("isSubscribed")
    @Expose
    private Boolean isSubscribed;

    public Boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(Boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

}
