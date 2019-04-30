package com.gdou.jianyue.nativeplugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.proxy.MethodProxy;
import com.gdou.jianyue.utils.TextUtils;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class StartActivityPlugin implements MethodChannel.MethodCallHandler {

    private Context context;

    public StartActivityPlugin(Context context) {
        this.context = context;
    }
    private static final  String METHOD_START_WITH_NO_INFO= "startMusicActivityWithoutInfo";
    private static final  String METHOD_START_FROM_BAR= "startMusicActivityFromBar";

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {

        switch (methodCall.method){
            case METHOD_START_WITH_NO_INFO:
                Bundle bundleFromFlutter = new Bundle();
                String songid = methodCall.argument("songid");
                String songName = methodCall.argument("songname");
                String artists = methodCall.argument("artistname");
                bundleFromFlutter.putLong(Constants.SONG_ID,Long.parseLong(songid));
                bundleFromFlutter.putString(Constants.SONG_NAME, TextUtils.removeExcess(songName));
                bundleFromFlutter.putString(Constants.ARTISTS,artists);

                MethodProxy.startMusicActivityWithoutInfo((Activity)context,bundleFromFlutter);
                result.success(null);
                break;
            case METHOD_START_FROM_BAR:
                MethodProxy.startMusicActivityFromBar((Activity)context);
                break;

        }

    }
}
