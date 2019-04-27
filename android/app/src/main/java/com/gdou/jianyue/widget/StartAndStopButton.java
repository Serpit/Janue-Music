package com.gdou.jianyue.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.gdou.jianyue.R;

public class StartAndStopButton extends ImageView {
    private Context mContext;
    public StartAndStopButton(Context context) {
        this(context,null);

    }

    public StartAndStopButton(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public StartAndStopButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setIsPlaying(false);
    }

    public void setIsPlaying(boolean isPlaying){
        if (isPlaying){
            setBackground(mContext.getDrawable(R.drawable.ic_play_btn_pause));
        }else {
            setBackground(mContext.getDrawable(R.drawable.ic_play_btn_play));
        }
    }
}
