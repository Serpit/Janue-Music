package com.gdou.jianyue.proxy;

import android.content.Context;

import com.gdou.jianyue.flutterplugin.DatabasePlugin;
import com.gdou.jianyue.flutterplugin.StartActivityPlugin;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class MethodChannelMap {
    static Map<String, MethodChannel.MethodCallHandler> map = new HashMap<>();


    private static final String START_ACTIVITY_CHANNEL = "janeMusic.flutter.io/startActivity";
    private static final String DATA_BASE_CHANNEL = "janeMusic.flutter.io/database";



    public static Map<String, MethodChannel.MethodCallHandler> getMap (Context context){
        map.put(START_ACTIVITY_CHANNEL,new StartActivityPlugin(context));
        map.put(DATA_BASE_CHANNEL,new DatabasePlugin(context));
        return  map;
    }




}
