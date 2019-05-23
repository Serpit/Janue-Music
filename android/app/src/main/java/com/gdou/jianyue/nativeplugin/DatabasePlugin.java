package com.gdou.jianyue.nativeplugin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.proxy.MethodProxy;
import com.gdou.jianyue.utils.TextUtils;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class DatabasePlugin implements MethodChannel.MethodCallHandler {

    private static final  String TAG= "DatabasePlugin";
    private static final  String METHOD_INSERT = "insertPlaying";
    private static final  String METHOD_QUERY = "queryRecent";
    private static final  String METHOD_DELETE = "update";
    private static final  String METHOD_INSERT_COLLECTION = "insertCollection";
    private static final  String METHOD_DELETE_DOWNLOAD_MUSIC_ON_LIST = "deleteDownloadMusicOnList";
    private static final  String METHOD_DELETE_DOWNLOAD_MUSIC = "deleteDownloadMusic";
    private static final String METHOD_QUERY_DOWNLOAD_MUSIC = "queryDownload";
    private Context mContext;

    public DatabasePlugin(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        switch (methodCall.method){
            case METHOD_QUERY:
                MethodProxy.queryAllRecentMusic(result);
                break;
            case METHOD_DELETE:
                break;
            case METHOD_INSERT_COLLECTION:
                MethodProxy.insertCollection(methodCall);
                break;
            case METHOD_INSERT:
                Log.d(TAG,methodCall.arguments.toString());
                MethodProxy.addIntoPlayingList(methodCall);
                Bundle musicBundle = new Bundle();
                musicBundle.putString(Constants.SONG_NAME, TextUtils.removeExcess(methodCall.argument("title")));
                musicBundle.putString(Constants.ARTISTS,methodCall.argument("author"));
                musicBundle.putLong(Constants.SONG_ID,Long.parseLong(methodCall.argument("song_id")));
                MethodProxy.startMusicActivity((FlutterActivity)mContext,musicBundle);
                result.success(null);
                break;
            case METHOD_QUERY_DOWNLOAD_MUSIC:
                MethodProxy.queryDownloadMusic(result);
                break;
            case METHOD_DELETE_DOWNLOAD_MUSIC:
                MethodProxy.deleteDownloadMusic(methodCall);
                break;
            case METHOD_DELETE_DOWNLOAD_MUSIC_ON_LIST:
                MethodProxy.deleteDownloadMusicOnList(methodCall);
                break;
        }
    }


}
