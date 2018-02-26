package com.example.d.healthbook.Models.DiaryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 26.09.2017.
 */

public class ParameterType {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("measureName")
    @Expose
    private String measureName;
    @SerializedName("aggregationFunction")
    @Expose
    private String aggregationFunction;
    @SerializedName("availableValues")
    @Expose
    private List<Object> availableValues = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    public String getAggregationFunction() {
        return aggregationFunction;
    }

    public void setAggregationFunction(String aggregationFunction) {
        this.aggregationFunction = aggregationFunction;
    }

    public List<Object> getAvailableValues() {
        return availableValues;
    }

    public void setAvailableValues(List<Object> availableValues) {
        this.availableValues = availableValues;
    }
}
