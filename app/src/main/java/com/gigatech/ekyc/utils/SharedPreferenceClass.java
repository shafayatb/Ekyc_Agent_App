package com.gigatech.ekyc.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

    public static void saveVal(Context context,String key,String value){

        SharedPreferences sharedPreferences = context.getSharedPreferences("saveValue",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();

    }

    public static String getVal(Context context,String key){

        SharedPreferences sharedPreferences = context.getSharedPreferences("saveValue",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,null);
    }

    public static void saveIsFirstTime(Context context,boolean value){

        SharedPreferences sharedPreferences = context.getSharedPreferences("saveValue",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IsFirstTime",value);
        editor.apply();

    }

    public static Boolean getIsFirstTime(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("saveValue",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsFirstTime",true);
    }

}
