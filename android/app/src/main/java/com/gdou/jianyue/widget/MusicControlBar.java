package com.gdou.jianyue.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.gdou.jianyue.R;

public class MusicControlBar extends FrameLayout implements View.OnClickListener {
    private ImageView iv_next,iv_last;
    private StartAndStopButton startAndStopButton;
    private OnMusicControlEvent event;
    public MusicControlBar(Context context) {
        this(context,null);
    }

    public MusicControlBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MusicControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context){
         View rootView = LayoutInflater.from(context).inflate(R.layout.layout_music_control_bar,this);
         iv_next = rootView.findViewById(R.id.iv_next);
         iv_last = rootView.findViewById(R.id.iv_last);
         startAndStopButton = rootView.findViewById(R.id.iv_start_stop);
         iv_next.setOnClickListener(this);
         iv_last.setOnClickListener(this);
         startAndStopButton.setOnClickListener(this);
    }

    public void setIsPlaying(boolean isPlaying){
        startAndStopButton.setIsPlaying(isPlaying);
    }
    public void setOnMusicControlEvent(OnMusicControlEvent event) {
        this.event = event;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_last:
                event.last();
                break;
            case R.id.iv_next:
                event.next();
                break;
            case R.id.iv_start_stop:
                event.startAndStop();
                break;
        }
    }

    public interface OnMusicControlEvent{
        void startAndStop();
        void next();
        void last();
    }
}
