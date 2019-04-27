package com.gdou.jianyue.proxy;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.utils.ObjectUtils;

import java.util.Map;
import java.util.function.BiConsumer;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class MethodChannelManager {

    private static MethodChannelManager instance = new MethodChannelManager();
    private MethodChannelManager() {
    }

    public static MethodChannelManager getInstance(){
        if (ObjectUtils.isNotNull(instance)){
            return instance;
        }else {
            return new MethodChannelManager();
        }
    }


    public void registerMethodChannel(FlutterView flutterview){
        Map<String, MethodChannel.MethodCallHandler> map = MethodChannelMap.getMap(flutterview.getContext());
        for (Map.Entry<String, MethodChannel.MethodCallHandler> entry:map.entrySet()){
            new MethodChannel(flutterview,entry.getKey()).setMethodCallHandler(entry.getValue());
        }
    }


}
