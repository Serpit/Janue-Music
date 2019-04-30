package com.gdou.jianyue.utils;

public class TextUtils {
    public static String removeExcess(String str){
        if(str.contains("（")){
           return str.substring(0,str.indexOf("（"));
        }else {
           return str;
        }
    }
}
