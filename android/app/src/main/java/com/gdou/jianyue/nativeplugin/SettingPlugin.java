package com.gdou.jianyue.nativeplugin;

import android.content.Context;

import com.gdou.jianyue.proxy.MethodProxy;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class SettingPlugin implements MethodChannel.MethodCallHandler {
    private Context context;
    private static final  String METHOD_SWITCH_MOBILE_PLAY= "setMobilePlayEnable";
    private static final String METHOD_SWITCH_MOBILE_DOWNLOAD = "setMobileDownLoadEnable";
    private static final String METHOD_GET_SETTING_INFO = "getSettingInfo";

    public SettingPlugin(Context context) {
        this.context = context;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method){
            case METHOD_GET_SETTING_INFO:
                MethodProxy.getSettingInfo(result);
                break;
            case METHOD_SWITCH_MOBILE_PLAY:
                MethodProxy.setMobilePlayEnable(methodCall);
                break;
            case METHOD_SWITCH_MOBILE_DOWNLOAD:
                MethodProxy.setMobileDownloadEnable(methodCall);
                break;
        }
    }
}
