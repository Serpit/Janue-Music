package com.gdou.jianyue.music.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gdou.jianyue.music.controller.MusicController;

public abstract class BaseMusicActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initMusicList();
    }

    public abstract void initViews();
    public abstract void findViews();
    public abstract void initMusicList();
    public abstract void initData();


}
