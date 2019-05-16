package com.gdou.jianyue.proxy;

import android.content.Context;

import com.gdou.jianyue.nativeplugin.DatabasePlugin;
import com.gdou.jianyue.nativeplugin.MusicControlPlugin;
import com.gdou.jianyue.nativeplugin.SettingPlugin;
import com.gdou.jianyue.nativeplugin.StartActivityPlugin;
import com.gdou.jianyue.nativeplugin.UserPlugin;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class MethodChannelMap {
    static Map<String, MethodChannel.MethodCallHandler> map = new HashMap<>();


    private static final String START_ACTIVITY_CHANNEL = "janeMusic.flutter.io/startActivity";
    private static final String DATA_BASE_CHANNEL = "janeMusic.flutter.io/database";
    private static final String USER_CHANNEL = "janeMusic.flutter.io/user";
    private static final String MUSIC_CONTROL_CHANNEL = "janeMusic.flutter.io/controlMusic";
    private static final String SETTING_CHANNEL = "janeMusic.flutter.io/setting";
    public static Map<String, MethodChannel.MethodCallHandler> getMap (Context context){
        map.put(START_ACTIVITY_CHANNEL,new StartActivityPlugin(context));
        map.put(DATA_BASE_CHANNEL,new DatabasePlugin(context));
        map.put(MUSIC_CONTROL_CHANNEL,new MusicControlPlugin(context));
        map.put(USER_CHANNEL,new UserPlugin(context));
        map.put(SETTING_CHANNEL,new SettingPlugin(context));
        return  map;
    }




}
