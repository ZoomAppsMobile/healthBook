package com.example.d.healthbook.Models.Chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 21.08.2017.
 */

public class Participant {
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
