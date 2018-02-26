package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by D on 19.06.2017.
 */
public class ResponseChannel {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("list")
    @Expose
    private List<ListChannel> list = null;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<ListChannel> getList() {
        return list;
    }

    public void setList(List<ListChannel> list) {
        this.list = list;
    }

}