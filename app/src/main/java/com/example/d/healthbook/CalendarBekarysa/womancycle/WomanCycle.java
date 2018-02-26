package com.example.d.healthbook.CalendarBekarysa.womancycle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WomanCycle implements Serializable {

@SerializedName("data_begin")
@Expose
private String dataBegin;


@SerializedName("id")
@Expose
private String id;

public String getDataBegin() {
return dataBegin;
}

public void setDataBegin(String dataBegin) {
this.dataBegin = dataBegin;
}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}