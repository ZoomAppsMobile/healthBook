package com.example.d.healthbook.GlobalVariables;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by User on 17.08.2017.
 */

public class PrefSingleton {
    private static PrefSingleton mInstance;

    private SharedPreferences mMyPreferences;

    private PrefSingleton(){}

    public static PrefSingleton getInstance(){
        if(mInstance==null){
            mInstance = new PrefSingleton();
        }
        return mInstance;
    }
    public void Initialize(Context ctxt){
        mMyPreferences = PreferenceManager.getDefaultSharedPreferences(ctxt);
    }

    public SharedPreferences getPreference(){
        return mMyPreferences;
    }

    public <E> void setValue (String key,E value){
        SharedPreferences.Editor e = mMyPreferences.edit();
        if (value instanceof Boolean){
            e.putBoolean(key, (Boolean) value);
        }
        if(value instanceof String){
            e.putString(key, (String) value);
        }
        if(value instanceof Integer){
            e.putInt(key, (Integer) value);
        }
        e.apply();
    }

    public <E> Object getValue(String key , E defaultValue){
        if (defaultValue instanceof Boolean){
            return mMyPreferences.getBoolean(key, (Boolean) defaultValue);
        }
        if(defaultValue instanceof String){
            return mMyPreferences.getString(key, (String) defaultValue);
        }
        if(defaultValue instanceof Integer){
            return mMyPreferences.getInt(key, (Integer) defaultValue);
        }
        return defaultValue;
    }

}
