package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MentionsModel {
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("numfound")
    @Expose
    private String numfound;
    @SerializedName("documents")
    @Expose
    private List<MentionsModel> documents = null;
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("is_anonymous")
    @Expose
    private String isAnonymous;
    @SerializedName("entity_id")
    @Expose
    private Integer entityId;
    @SerializedName("entity_type")
    @Expose
    private Integer entityType;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("performance_mark")
    @Expose
    private String performanceMark;
    @SerializedName("efficiency_mark")
    @Expose
    private String efficiencyMark;
    @SerializedName("recommendation_mark")
    @Expose
    private String recommendationMark;
    @SerializedName("created_time")
    @Expose
    private String createdTime;
    @SerializedName("answer")
    @Expose
    private String answer;


    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getNumfound() {
        return numfound;
    }

    public void setNumfound(String numfound) {
        this.numfound = numfound;
    }

    public List<MentionsModel> getDocuments() {
        return documents;
    }

    public void setDocuments(List<MentionsModel> documents) {
        this.documents = documents;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPerformanceMark() {
        return performanceMark;
    }

    public void setPerformanceMark(String performanceMark) {
        this.performanceMark = performanceMark;
    }

    public String getEfficiencyMark() {
        return efficiencyMark;
    }

    public void setEfficiencyMark(String efficiencyMark) {
        this.efficiencyMark = efficiencyMark;
    }

    public String getRecommendationMark() {
        return recommendationMark;
    }

    public void setRecommendationMark(String recommendationMark) {
        this.recommendationMark = recommendationMark;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
