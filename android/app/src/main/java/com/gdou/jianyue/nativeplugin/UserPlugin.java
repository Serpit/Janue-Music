package com.gdou.jianyue.nativeplugin;


import android.content.Context;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.proxy.MethodProxy;
import com.gdou.jianyue.utils.SPUtils;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class UserPlugin implements MethodChannel.MethodCallHandler {
    private Context context;

    private static final String SAVE_USER_INFO = "saveUserInfo";
    private static final String GET_USER_IS_LOGIN = "getUserIsLogin";
    private static final String LOGOUT = "logout";
    private static final String GET_USERID = "getUserId";
    public UserPlugin(Context context) {
        this.context = context;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method){
            case SAVE_USER_INFO:
                MethodProxy.saveUserInfo(methodCall);
                break;
            case GET_USER_IS_LOGIN:
                MethodProxy.getUserIsLogin(result);
                break;
            case LOGOUT:
                MethodProxy.logout();
                break;
            case GET_USERID:
                MethodProxy.getUserId(result);
                break;
        }
    }
}
