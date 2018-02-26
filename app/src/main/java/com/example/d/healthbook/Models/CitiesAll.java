package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Asus on 23.02.2018.
 */

public class CitiesAll {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name_kaz")
    @Expose
    private String nameKaz;
    @SerializedName("name_eng")
    @Expose
    private String nameEng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKaz() {
        return nameKaz;
    }

    public void setNameKaz(String nameKaz) {
        this.nameKaz = nameKaz;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }
}
