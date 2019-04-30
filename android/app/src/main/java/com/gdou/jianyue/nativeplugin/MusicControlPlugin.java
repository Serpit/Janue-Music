package com.gdou.jianyue.nativeplugin;

import android.content.Context;

import com.gdou.jianyue.proxy.MethodProxy;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MusicControlPlugin implements MethodChannel.MethodCallHandler {

    private Context context;

    private static final String METHOD_START_MUSIC = "startMusic";
    private static final String METHOD_STOP_MUSIC = "stopMusic";
    private static final String METHOD_GET_IS_PLAYING = "getIsPlaying";
    private static final String METHOD_GET_PLAYING_LIST = "getPlayingList";
    private static final String METHOD_GET_LOCAL_MUSIC_LIST = "getLocalMusicList";
    private static final String METHOD_SWITCH_MUSIC = "switchMusic";
    private static final String METHOD_GET_CURRENT_INDEX = "getCurIndex";
    public MusicControlPlugin(Context context) {
        this.context = context;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method){
            case METHOD_START_MUSIC:
                MethodProxy.startMusic(context);
                break;
            case METHOD_STOP_MUSIC:
                MethodProxy.stopMusic(context);
                break;
            case METHOD_GET_IS_PLAYING:
                MethodProxy.getIsPlaying(result);
                break;
            case METHOD_GET_PLAYING_LIST:
                MethodProxy.getPlayingList(result);
                break;
            case METHOD_GET_LOCAL_MUSIC_LIST:
                MethodProxy.getLocalMusicList(context,result);
                break;
            case METHOD_SWITCH_MUSIC:
                MethodProxy.switchMusic(methodCall);
                break;
            case METHOD_GET_CURRENT_INDEX:
                MethodProxy.getCurIndex(result);
                break;
        }
    }

}
