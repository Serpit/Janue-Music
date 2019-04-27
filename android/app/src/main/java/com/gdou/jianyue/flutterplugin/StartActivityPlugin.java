package com.gdou.jianyue.flutterplugin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.proxy.MethodProxy;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class StartActivityPlugin implements MethodChannel.MethodCallHandler {

    private Context context;

    public StartActivityPlugin(Context context) {
        this.context = context;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
         Bundle bundleFromFlutter = new Bundle();
        String songName = methodCall.argument(Constants.SONG_NAME);
        String artists = methodCall.argument(Constants.ARTISTS);
        bundleFromFlutter.putString(Constants.SONG_NAME,songName);
        bundleFromFlutter.putString(Constants.ARTISTS,artists);

        MethodProxy.startMusicActivity((Activity)context,bundleFromFlutter);
        result.success(null);
    }
}
