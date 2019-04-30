package com.gdou.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.gdou.jianyue.utils.ObjectUtils;

public class ShareProxy {
    private static ShareProxy instance = new ShareProxy();
    private ShareProxy(){}

    public static ShareProxy getInstance(){
        if (ObjectUtils.isNotNull(instance)){
            return instance;
        }else {
            return new ShareProxy();
        }
    }

    public void shareText(Context context,String shareContent){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT,shareContent);
        context.startActivity(Intent.createChooser(textIntent,"分享"));
    }


}
