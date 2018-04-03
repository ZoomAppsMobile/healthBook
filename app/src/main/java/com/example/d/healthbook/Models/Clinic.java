package com.example.d.healthbook.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clinic {
    @SerializedName("numfound")
    @Expose
    private Integer numfound;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("documents")
    @Expose
    private List<Document> documents = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("speciality")
    @Expose
    private Object speciality;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phones")
    @Expose
    private String phones;
    @SerializedName("emails")
    @Expose
    private Object emails;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("performance_rate")
    @Expose
    private Object performanceRate;
    @SerializedName("efficiency_rate")
    @Expose
    private Object efficiencyRate;
    @SerializedName("quality_price_rate")
    @Expose
    private Object qualityPriceRate;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("mention_count")
    @Expose
    private Integer mentionCount;
    @SerializedName("expert_count")
    @Expose
    private Integer expertCount;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;
    @SerializedName("expert_names")
    @Expose
    private List<String> expertNames = null;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("analysis_count")
    @Expose
    private Integer analysisCount;
    @SerializedName("analysis_names")
    @Expose
    private String analysisNames;
    @SerializedName("analysis_ids")
    @Expose
    private Object analysisIds;

    public Integer getNumfound() {
        return numfound;
    }

    public void setNumfound(Integer numfound) {
        this.numfound = numfound;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Object getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Object speciality) {
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public Object getEmails() {
        return emails;
    }

    public void setEmails(Object emails) {
        this.emails = emails;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Object getPerformanceRate() {
        return performanceRate;
    }

    public void setPerformanceRate(Object performanceRate) {
        this.performanceRate = performanceRate;
    }

    public Object getEfficiencyRate() {
        return efficiencyRate;
    }

    public void setEfficiencyRate(Object efficiencyRate) {
        this.efficiencyRate = efficiencyRate;
    }

    public Object getQualityPriceRate() {
        return qualityPriceRate;
    }

    public void setQualityPriceRate(Object qualityPriceRate) {
        this.qualityPriceRate = qualityPriceRate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getMentionCount() {
        return mentionCount;
    }

    public void setMentionCount(Integer mentionCount) {
        this.mentionCount = mentionCount;
    }

    public Integer getExpertCount() {
        return expertCount;
    }

    public void setExpertCount(Integer expertCount) {
        this.expertCount = expertCount;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<String> getExpertNames() {
        return expertNames;
    }

    public void setExpertNames(List<String> expertNames) {
        this.expertNames = expertNames;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAnalysisCount() {
        return analysisCount;
    }

    public void setAnalysisCount(Integer analysisCount) {
        this.analysisCount = analysisCount;
    }

    public String getAnalysisNames() {
        return analysisNames;
    }

    public void setAnalysisNames(String analysisNames) {
        this.analysisNames = analysisNames;
    }

    public Object getAnalysisIds() {
        return analysisIds;
    }

    public void setAnalysisIds(Object analysisIds) {
        this.analysisIds = analysisIds;
    }

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

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

}