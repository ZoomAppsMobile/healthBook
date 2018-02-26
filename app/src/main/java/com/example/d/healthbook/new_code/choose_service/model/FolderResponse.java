package com.example.d.healthbook.new_code.choose_service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolderResponse {

@SerializedName("id")
@Expose
private String id;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}}
