package com.example.d.healthbook.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clinic {

@SerializedName("id")
@Expose
public String id;
@SerializedName("name")
@Expose
public String name;
@SerializedName("company_id")
@Expose
public String companyId;
@SerializedName("city_id")
@Expose
public String cityId;
@SerializedName("city")
@Expose
public String city;
@SerializedName("address")
@Expose
public String address;
@SerializedName("photo")
@Expose
public String photo;
@SerializedName("info")
@Expose
public String info;
@SerializedName("emails")
@Expose
public String emails;
@SerializedName("phones")
@Expose
public String phones;
@SerializedName("longitude")
@Expose
public Object longitude;
@SerializedName("latitude")
@Expose
public Object latitude;
@SerializedName("mentions")
@Expose
public List<Mention> mentions = null;
@SerializedName("rate")
@Expose
public String rate;
@SerializedName("performance_rate")
@Expose
public Object performanceRate;
@SerializedName("efficiency_rate")
@Expose
public Object efficiencyRate;
@SerializedName("quality_price_rate")
@Expose
public Object qualityPriceRate;

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
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

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
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
}