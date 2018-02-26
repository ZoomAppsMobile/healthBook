package com.example.d.healthbook.Bottom_helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by UserPC on 10.05.2016.
 */
public class SharedPreference {

    static final String USER_NUMBER = "username";
    static final String USER_SESSION = "check_box";
    static final String VERSION= "version";
    static final String PREF_USER_TYPE= "type";
    static final String PREF_NAME= "name";
    static final String GALLERY_PHOTO= "gallery";

    static final String PUSH_NEWS_STATE = "c1";
    static final String TOP_USERS_CASH= "top_users";
    static final String PUSH_DISCOUNTS_STATE = "c2";

    static final String GRID_STATE = "ava";
    static final String POL= "pol";

    static final String LAT="lat";;
    static final String LONG="lot";;
    static final String SAVE_SWITCTH_STATE= "save_switch_state";


    static final String LOCALE_APP = "age";
    static final String CURRENT_COMPANY = "date_registr";
    static final String CITY = "ser";
    static final String DESC= "ZXCZXC";
    static final String CITY_NAME = "serer2";
    static final String REG= "r2";



    static final String CATEORY = "v1";
    static final String PANEL_STATE="pan";




    static final String CITY_ID = "vczxc2";
    static final String CAT_NAME = "jhhjh";


    static final String LOAD_MORE_STATES = "crsdsd";
    static final String CRM = "crm";
    static final String CATEGORY_SESSION = "crm2";














    static final String TOKEN ="pan";



    static final String LOCAL_STREET = "local_street";
    static final String LOCAL_HOME = "crmxxx";
    static final String LOCAL_KORPUS = "date_registrxx";
    static final String LOCAL_STRORNIE = "polxx";
    static final String LOCAL_FLAT = "vczxc2xx";
    static final String LOCAL_FLOOR = "jhhjhxx";
    static final String LOCAL_ENTR = "serer22xx";
    static final String LOCAL_NOTE = "avaxxx";
    static final String CURENT_CATEGORY = "serer22";




    public static void setLocalNote(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_NOTE, r2);
        editor.apply();
    }
    public static String getLocalNote(Context ctx) {
        return getSharedPreferences(ctx).getString(LOCAL_NOTE, "");
    }



    public static void setLocalEntr(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_ENTR, r2);
        editor.apply();
    }
    public static String getLocalEntr(Context ctx) {
        return getSharedPreferences(ctx).getString(LOCAL_ENTR, "");
    }






    public static void setLocalFloor(Context ctx, String v1)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_FLOOR,v1);
        editor.apply();
    }

    public static String getLocalFloor(Context ctx) {
        return getSharedPreferences(ctx).getString(LOCAL_FLOOR, "");
    }





    public static void setLocalFlat(Context ctx, String v1)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_FLAT, v1);
        editor.apply();
    }

    public static String getLocalFlat(Context ctx) {
        return getSharedPreferences(ctx).getString(LOCAL_FLAT, "");
    }


    public static String getToken(Context ctx)
    {
        return getSharedPreferences(ctx).getString(TOKEN, "");

    }



    public static void setToken(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(TOKEN, userName);
        editor.commit();
    }



    public static void setLocalStrornie(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_STRORNIE, userName);
        editor.commit();
    }

    public static String getLocalStrornie(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LOCAL_STRORNIE, "");

    }

    public static void setLocalKorpus(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_KORPUS, userName);
        editor.commit();
    }

    public static String getLocalKorpus(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LOCAL_KORPUS, "");

    }
    public static void setLocalHome(Context ctx, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_HOME, userType);
        editor.commit();
    }

    public static String getLocalHome(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LOCAL_HOME, "");

    }


    public static void setLocalStreet(Context ctx, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCAL_STREET, userType);
        editor.commit();
    }

    public static String getLocalStreet(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LOCAL_STREET, "");

    }






    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }




    public static void setSaveSwitcthState(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SAVE_SWITCTH_STATE, userName);
        editor.commit();
    }



    public static String getTopUsersCash(Context ctx)
    {
        return getSharedPreferences(ctx).getString(TOP_USERS_CASH, "");

    }




    public static void setTopUsersCash(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(TOP_USERS_CASH, userName);
        editor.commit();
    }



    public static String getSaveSwitcthState(Context ctx)
    {
        return getSharedPreferences(ctx).getString(SAVE_SWITCTH_STATE, "");

    }



    public static String getPeriod(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PANEL_STATE, "");

    }



    public static void setPeriod(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PANEL_STATE, userName);
        editor.commit();
    }

    public static void setWomanduration(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LAT, userName);
        editor.commit();
    }

    public static String getWomanduration(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LAT, "");

    }





    public static String getDataBegin(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LONG, "");

    }




    public static void setDataBegin(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LONG, userName);
        editor.commit();
    }


    public static void setDataBegin2(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(VERSION, userName);
        editor.commit();
    }

    public static String getDataBegin2(Context ctx)
    {
        return getSharedPreferences(ctx).getString(VERSION, "");

    }



    public static void setUserSession(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_SESSION, userName);
        editor.commit();
    }

    public static String getUserSession(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_SESSION, "");

    }



    public static void setNumber(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_NUMBER, userName);
        editor.commit();
    }

    public static String getNumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_NUMBER, "");

    }
    public static void setWomanPeriod(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CURRENT_COMPANY, userName);
        editor.commit();
    }

    public static String getWomanPeriod(Context ctx)
    {
        return getSharedPreferences(ctx).getString(CURRENT_COMPANY, "");

    }

    public static void setLocaleApp(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOCALE_APP, userName);
        editor.commit();
    }

    public static String getLocaleApp(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LOCALE_APP, "");

    }




    public static void setPol(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(POL, userName);
        editor.commit();
    }

    public static String getPol(Context ctx)
    {
        return getSharedPreferences(ctx).getString(POL, "");

    }












    public static void setGalleryPhoto(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(GALLERY_PHOTO, userName);
        editor.commit();
    }

    public static String getGalleryPhoto(Context ctx)
    {
        return getSharedPreferences(ctx).getString(GALLERY_PHOTO, "");

    }













    public static void setName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NAME, userName);
        editor.commit();
    }

    public static String getName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_NAME, "");

    }
    public static void setDesc(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(DESC, userName);
        editor.commit();
    }

    public static String getDesc(Context ctx)
    {
        return getSharedPreferences(ctx).getString(DESC, "");

    }



    public static void setLoadMoreStates(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(LOAD_MORE_STATES, userName);
        editor.commit();
    }

    public static String getLoadMoreStates(Context ctx)
    {
        return getSharedPreferences(ctx).getString(LOAD_MORE_STATES, "");

    }






    public static void setUserType(Context ctx, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_TYPE, userType);
        editor.commit();
    }

    public static String getUserType(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_TYPE, "");

    }




    public static void setCrm(Context ctx, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CRM, userType);
        editor.commit();
    }

    public static String getCrm(Context ctx)
    {
        return getSharedPreferences(ctx).getString(CRM, "");

    }
    public static void setCategorySession(Context ctx, String userType)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CATEGORY_SESSION, userType);
        editor.commit();
    }

    public static String getCategorySession(Context ctx)
    {
        return getSharedPreferences(ctx).getString(CATEGORY_SESSION, "");

    }


//Регион

    public static void setReg(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(REG, r2);
        editor.apply();
    }
    public static String getReg(Context ctx) {
        return getSharedPreferences(ctx).getString(REG, "");
    }



    //Город
    public static void setCity(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CITY, r2);
        editor.apply();
    }
    public static String getCity(Context ctx) {
        return getSharedPreferences(ctx).getString(CITY, "");
    }


    public static void setCityName(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CITY_NAME, r2);
        editor.apply();
    }
    public static String getCityName(Context ctx) {
        return getSharedPreferences(ctx).getString(CITY_NAME, "");
    }

    public static void setCurentCategory(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CURENT_CATEGORY, r2);
        editor.apply();
    }
    public static String getCurentCategory(Context ctx) {
        return getSharedPreferences(ctx).getString(CURENT_CATEGORY, "");
    }




    //Категорий

    public static void setCateory(Context ctx, String v1)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CATEORY, v1);
        editor.apply();
    }

    public static String getCateory(Context ctx) {
        return getSharedPreferences(ctx).getString(CATEORY, "");
    }




    public static void setId(Context ctx, String v1)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CITY_ID, v1);
        editor.apply();
    }

    public static String getId(Context ctx) {
        return getSharedPreferences(ctx).getString(CITY_ID, "");
    }




 public static void setCatName(Context ctx, String v1)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(CAT_NAME ,v1);
        editor.apply();
    }

    public static String getCatName(Context ctx) {
        return getSharedPreferences(ctx).getString(CAT_NAME, "");
    }





    public static void setPushNewsState(Context ctx, String c1)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PUSH_NEWS_STATE, c1);
        editor.apply();
    }

    public static String getPushNewsState(Context ctx) {
        return getSharedPreferences(ctx).getString(PUSH_NEWS_STATE, "");
    }

    public static void setPushDiscountsState(Context ctx, String c2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PUSH_DISCOUNTS_STATE, c2);
        editor.apply();
    }

    public static String getPushDiscountsState(Context ctx) {
        return getSharedPreferences(ctx).getString(PUSH_DISCOUNTS_STATE, "");
    }

    public static void setGridState(Context ctx, String r2)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(GRID_STATE, r2);
        editor.apply();
    }
    public static String getGridState(Context ctx) {
        return getSharedPreferences(ctx).getString(GRID_STATE, "");
    }






    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }
    public static void clearV1(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }


}
