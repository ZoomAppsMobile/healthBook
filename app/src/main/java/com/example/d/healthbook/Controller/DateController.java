package com.example.d.healthbook.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 10.08.2017.
 */

public class DateController {
    public static final int  YEAR = 1;
    public static final String default_format_with_time = "yyyy-MM-dd HH:mm:ss";
    public static final String default_format = "yyyy-MM-dd";


    private static Calendar convertDateToCalendar(String format , String str_date){
        Date date = null;
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        try {
            date = format1.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String getDateDifference(String str_date , Integer type){
        Calendar cal = convertDateToCalendar(default_format,str_date);
        if(cal!=null)
            return getByType(type, cal);
        else
            return "";
    }

    public static String getDateDifferenceWithCondition(String str_date , Integer type ){
        Calendar cal = convertDateToCalendar(default_format,str_date);
        if(cal.get(Calendar.YEAR)<1000)
            return null;
        if(cal!=null)
            return getByType(type, cal);
        else
            return "";
    }

    private static String getByType(Integer type , Calendar cal){
        Calendar cal_now = Calendar.getInstance();
        switch (type){
            case YEAR:{
                Integer YearDif =  cal_now.get(Calendar.YEAR) - cal.get(Calendar.YEAR) ;
                return YearDif.toString();
            }
            default:
                break;
        }
        return "";
    }

    public static String convertFormat(String cur_format , String  out_format , String str_date){
        Calendar cal = convertDateToCalendar(cur_format,str_date );
        if(cal!=null) {
            SimpleDateFormat format = new SimpleDateFormat(out_format);
            try {
                String out_date = (format.format(cal.getTime()));
                return out_date;
            }
            catch (Exception ex) {
            }
        }
        return "";
    }
}
