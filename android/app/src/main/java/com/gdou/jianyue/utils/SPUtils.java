package com.gdou.jianyue.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.JanueMusicApplication;

public class SPUtils {
    private static SharedPreferences sp = JanueMusicApplication.getContext().getSharedPreferences(Constants.SP_MUSIC, Context.MODE_PRIVATE);
    public static void saveString(String key,String string){
        sp.edit().putString(key,string).apply();
    }
    public static void saveBoolean(String key,boolean bool){
        sp.edit().putBoolean(key,bool).apply();
    }

    public static String getString(String key){
        return sp.getString(key,"");
    }

    public static boolean getBoolean(String key){
        return sp.getBoolean(key,false);
    }


    public static void saveInt(String key,int value){
        sp.edit().putInt(key,value).apply();
    }
    public static int getInt(String key){
        return sp.getInt(key,0);
    }
}
