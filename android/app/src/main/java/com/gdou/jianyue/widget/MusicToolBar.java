package com.gdou.jianyue.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdou.jianyue.R;

public class MusicToolBar extends FrameLayout {

    private String songName;
    private String artists;
    private TextView tv_songName;
    private TextView tv_artists;
    private ImageView iv_back;
    private ImageView iv_share;
    private RelativeLayout rootView;
    private Context mContext;

    public MusicToolBar(@NonNull Context context) {
        this(context,null);

    }

    public MusicToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MusicToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
        initListener();
    }


    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_music_toolbar,this);
        rootView = findViewById(R.id.rootlayout);
        tv_artists = rootView.findViewById(R.id.tv_artists);
        tv_songName = rootView.findViewById(R.id.tv_songname);
        iv_back = rootView.findViewById(R.id.iv_back);
        iv_share = rootView.findViewById(R.id.iv_share);

    }

    public void setShareOnClickListener(OnClickListener listener){
        iv_share.setOnClickListener(listener);
    }
    private void initListener(){

        if (mContext instanceof Activity){
            iv_back.setOnClickListener(view -> {
                ((Activity) mContext).finish();
            });
        }


    }



    public void setSongName(String songName){
        tv_songName.setText(songName);
    }

    public void setArtists(String artists){
        tv_artists.setText(artists);
    }



}
