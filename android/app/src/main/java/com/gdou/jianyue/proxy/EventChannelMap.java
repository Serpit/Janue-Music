package com.gdou.jianyue.proxy;

import android.content.Context;

import com.gdou.jianyue.flutterplugin.MusicPlugin;

import java.util.HashMap;
import java.util.Map;
import io.flutter.plugin.common.EventChannel;


public class EventChannelMap {

    private static final String MUSIC_EVENT_CHANNEL = "janeMusic.flutter.io/musicEventChannel";

    static Map<String, EventChannel.StreamHandler> map = new HashMap<>();
    public static Map<String, EventChannel.StreamHandler> getMap (Context context){
        map.put(MUSIC_EVENT_CHANNEL,new MusicPlugin());
        return  map;
    }
}
