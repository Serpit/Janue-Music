package com.gdou.jianyue.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.gdou.jianyue.R;
import com.gdou.jianyue.utils.ObjectUtils;

public class RotateImageView extends FrameLayout {
    private ImageView iv_album;
    private ObjectAnimator animator;
    private boolean isPause = false;
    private Context context;
    public RotateImageView(Context context) {
        this(context,null);
    }

    public RotateImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RotateImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }


    public void initInAlbumImage(String url){
        if (ObjectUtils.isNotNull(context)){
            Glide.with(context).load(url).error(getResources().getDrawable(R.drawable.play_page_default_cover)).into(iv_album);
        }
    }
    private void initView(Context context){


        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_rotate_image_view,this);
        iv_album = rootView.findViewById(R.id.iv_album1);
        animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        LinearInterpolator lin = new LinearInterpolator();
        animator.setInterpolator(lin);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(7000);
        animator.setRepeatCount(-1);

    }

    public void startRotate(){
        if (isPause){
            animator.resume();
            isPause = false;
        }else {
            animator.start();
        }

    }

    public void stopRoate(){
        animator.pause();
        isPause = true;
    }



}
