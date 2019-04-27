package com.gdou.jianyue.music.controller;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.utils.ObjectUtils;

import java.io.IOException;

public class MusicControllerImpl implements MusicController, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {
    private static final String TAG = MusicControllerImpl.class.getSimpleName();


    private boolean isPrepared = false;
    private boolean isFirstLoad;


    private static MediaPlayer mediaPlayer ;
    private MusicPlayList mMusicPlayList = MusicPlayList.getInstance();
    private static MusicController controller = new MusicControllerImpl();
    private static OnMusicPositionChangeListener positionChangeListener;
    private OnMusicStateChangeListener musicStateChangeListener;
    public static MusicController getInstance(){
        return controller;
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG,"mediaplayer is Prepared");
        isPrepared = true;
        musicStateChangeListener.onMusicParpred();
        start();
    }


    @Override
    public void setOnMusicPositionChangeListener(OnMusicPositionChangeListener listener) {
        this.positionChangeListener = listener;
    }

    private MusicControllerImpl() {
        mediaPlayer =  new MediaPlayer();
        initMediaPlayer();
    }

    @Override
    public int getMusicDuration() {
        if (ObjectUtils.isNotNull(mediaPlayer) && isPrepared){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public void initDataSorce(String url) {
        if (url!=null && url.isEmpty() ){
            isPrepared = false;
            return;
        }
        try{
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        }catch (IOException e){
            isPrepared = false ;
            e.printStackTrace();
        }
    }
    private void initMediaPlayer(){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Log.d(TAG,"initMediaPlayer");
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
    }

    /*移动到某个位置*/
    @Override
    public void seekTo(int position){
        mediaPlayer.seekTo(position);
    }

    @Override
    public void start() {

        if (isPrepared) {
            mediaPlayer.start();
            musicStateChangeListener.onMusicStart();
            new UpDateMusicPositionTask().execute();
        }else {
            initDataSorce(mMusicPlayList.getCurMusic().getDownloadLink());
        }

    }

    @Override
    public void stop() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            musicStateChangeListener.onMusicStop();
        }

    }

    @Override
    public void nextSong() {
        mediaPlayer.reset();
        initMediaPlayer();
        initDataSorce(mMusicPlayList.getNextMusic().getDownloadLink());
    }

    @Override
    public void lastSong() {
        mediaPlayer.reset();
        initMediaPlayer();
        initDataSorce(mMusicPlayList.getLastMusic().getDownloadLink());
    }

    @Override
    public boolean isPalying() {
        if (ObjectUtils.isNotNull(mediaPlayer)){
            return mediaPlayer.isPlaying();
        }
        return false;

    }

    @Override
    public boolean isParpred() {
        return isPrepared;
    }


    @Override
    public void setPlayMode(String mode) {
        switch (mode){
            case "play_mode_one":
                break;
            case "play_mode_loop":
                break;
            case "play_mode_shuffle":
                break;
        }
    }

    @Override
    public void setOnMusicStartAndStopListener(OnMusicStateChangeListener listener) {
        this.musicStateChangeListener = listener;
    }

    static class UpDateMusicPositionTask extends AsyncTask<Void,Integer,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            int curPosition = 0;

            while (curPosition<=mediaPlayer.getDuration() ){
                curPosition = mediaPlayer.getCurrentPosition();
                try {
                    Thread.sleep(1000);
                    publishProgress(curPosition);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }


            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
            if (ObjectUtils.isNotNull(positionChangeListener)){
                positionChangeListener.onMusicPositionChange(values[0]);
            }

        }
    }



}
