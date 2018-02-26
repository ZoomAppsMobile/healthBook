package com.example.d.healthbook.CalendarBekarysa.Calendar_UI;

/**
 * Created by Meirlen on 25.10.2017.
 */

public class Woman_day2 {

    private String date;
    private boolean criticDay;

    public Woman_day2(String date) {
        this.date = date;
    }

    public Woman_day2(String date, boolean criticDay) {
        this.date = date;
        this.criticDay = criticDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCriticDay() {
        return criticDay;
    }

    public void setCriticDay(boolean criticDay) {
        this.criticDay = criticDay;
    }
}
