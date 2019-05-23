package com.gdou.jianyue.others;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
        Bitmap origin = BlurBitmapUtil.doBlur(toTransform,10,15);
        return getBlackImage(origin);
    }
    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }

    public  Bitmap getBlackImage(Bitmap bm){
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(),bm.getHeight(),Bitmap.Config.RGB_565);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(bmp);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bm,0,0,paint);

        canvas.drawColor(Color.parseColor("#40000000"));
        return bmp;

    }


}