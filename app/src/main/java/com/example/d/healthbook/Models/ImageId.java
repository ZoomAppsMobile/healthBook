package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 08.09.2017.
 */

public class ImageId {

    @SerializedName("$id")
    @Expose
    private String $id;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

}
