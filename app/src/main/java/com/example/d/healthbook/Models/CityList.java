package com.example.d.healthbook.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Asus on 23.02.2018.
 */

public class CityList {
    @SerializedName("specialities")
    @Expose
    private List<Speciality> specialities = null;
    @SerializedName("cities")
    @Expose
    private List<City> cities = null;
    @SerializedName("citiesAll")
    @Expose
    private List<CitiesAll> citiesAll = null;

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<CitiesAll> getCitiesAll() {
        return citiesAll;
    }

    public void setCitiesAll(List<CitiesAll> citiesAll) {
        this.citiesAll = citiesAll;
    }
}
