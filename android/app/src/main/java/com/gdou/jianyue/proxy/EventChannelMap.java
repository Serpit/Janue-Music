package com.gdou.jianyue.proxy;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;
import io.flutter.plugin.common.EventChannel;


public class EventChannelMap {



    static Map<String, EventChannel.StreamHandler> map = new HashMap<>();
    public static Map<String, EventChannel.StreamHandler> getMap (Context context){

        return  map;
    }
}
