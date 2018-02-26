package com.example.d.healthbook.CalendarBekarysa.Models;

/**
 * Created by User on 08.07.2017.
 */

public class CalendarModel {
    public CalendarModel( Integer day,String date){
        this.day = day;
        this.date = date;
    }

    private Integer day;
    private Integer period;
    private String date;
    private boolean Note;
    private boolean Drugs;
    private boolean WomanDay1;
    private boolean evolition;
    private boolean WomanDay;


    public boolean isWomanDay() {
        return WomanDay;
    }

    public void setWomanDay(boolean womanDay) {
        WomanDay = womanDay;
    }

    public boolean isWomanDay1() {
        return WomanDay1;
    }

    public void setWomanDay1(boolean womanDay1) {
        WomanDay1 = womanDay1;
    }

    public boolean isDrugs() {
        return Drugs;
    }

    public void setDrugs(boolean drugs) {
        Drugs = drugs;
    }

    public boolean isNote() {
        return Note;
    }

    public void setNote(boolean note) {
        Note = note;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public boolean isEvolition() {
        return evolition;
    }

    public void setEvolition(boolean evolition) {
        this.evolition = evolition;
    }
}
