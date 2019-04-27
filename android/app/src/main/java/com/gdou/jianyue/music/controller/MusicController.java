package com.gdou.jianyue.music.controller;

public interface MusicController {


    void start();
    void stop();
    void nextSong();
    void lastSong();
    void seekTo(int position);
    boolean isPalying();
    boolean isParpred();
    int getMusicDuration();
    void switchMusic(long songid);
    void setOnMusicPositionChangeListener(OnMusicPositionChangeListener listener);
    void setOnMusicStartAndStopListener(OnMusicStateChangeListener listener);
    void setOnMusicChangeListener(OnMusicChangeListener listener);
    void setPlayMode(String mode);
    interface OnMusicPositionChangeListener{
        void onMusicPositionChange(int position);
    }
    interface OnMusicChangeListener{
        void onMusicChange(int position);
    }
    interface OnMusicStateChangeListener {
        void onMusicStart();
        void onMusicStop();
        void onMusicParpred();
    }

}
