package com.gdou.jianyue.others;

import android.content.Context;
import android.graphics.Bitmap;

import android.support.annotation.NonNull;


import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


import java.security.MessageDigest;

public class GlideBlurformation extends BitmapTransformation {
    private Context context;
    public GlideBlurformation(Context context) {
        this.context = context;
    }
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.doBlur(toTransform,10,15);
    }
    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }



}