package com.gdou.jianyue.music.controller;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.bean.SongResultBean;
import com.gdou.jianyue.music.model.MainMusicModel;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.ObjectUtils;
import com.gdou.jianyue.utils.SPUtils;

import java.io.IOException;
import java.util.Random;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MusicControllerImpl implements MusicController, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private static final String TAG = MusicControllerImpl.class.getSimpleName();


    private boolean isPrepared = false;
    private boolean isFirstLoad;

    private String playMode;
    private static MediaPlayer mediaPlayer ;
    private MusicPlayList mMusicPlayList = MusicPlayList.getInstance();
    private static MusicController controller = new MusicControllerImpl();
    private static OnMusicPositionChangeListener positionChangeListener;
    private OnMusicStateChangeListener musicStateChangeListener;
    private OnMusicChangeListener musicChangeListener;
    public static MusicController getInstance(){
        return controller;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        isPrepared = true;
        if (ObjectUtils.isNotNull(musicStateChangeListener)){
            musicStateChangeListener.onMusicParpred();
        }
        start();
    }


    @Override
    public void setOnMusicPositionChangeListener(OnMusicPositionChangeListener listener) {
        positionChangeListener = listener;
    }

    @Override
    public void setOnMusicChangeListener(OnMusicChangeListener listener) {
        musicChangeListener = listener;
    }

    private MusicControllerImpl() {
        mediaPlayer =  new MediaPlayer();
        playMode = SPUtils.getString(Constants.SP_KEY_PLAY_MODE);
        initMediaPlayer();
    }


    @Override
    public void resetPlayer() {
        mediaPlayer.stop();
    }

    @Override
    public void switchMusic(long songid) {
        mediaPlayer.reset();
        initMediaPlayer();
        if (ObjectUtils.isNull(mMusicPlayList.getMusciBySongId(songid).getDownloadLink())){
            MainMusicModel model = new MainMusicModel();
            Disposable disposable =  model.savePlayMusicLink(songid).subscribe(new Consumer<SongResultBean>() {
                @Override
                public void accept(SongResultBean songResultBean) throws Exception {
                    DatabaseUtils.inserMusicDownloadLink(songid,songResultBean.getBitrate().getFile_link());
                    MusicPlayList.getInstance().updateList();
                    initDataSorce(mMusicPlayList.getMusciBySongId(songid).getDownloadLink());
                    mMusicPlayList.setCurrentIndex(mMusicPlayList.getMusicIndexBySongId(songid));
                }
            });
        }else {
            initDataSorce(mMusicPlayList.getMusciBySongId(songid).getDownloadLink());
            mMusicPlayList.setCurrentIndex(mMusicPlayList.getMusicIndexBySongId(songid));
        }
        if (ObjectUtils.isNotNull(musicChangeListener)){
            musicChangeListener.onMusicChange(mMusicPlayList.getMusicIndexBySongId(songid));
        }

    }

    @Override
    public int getMusicDuration() {
        if (ObjectUtils.isNotNull(mediaPlayer) && isPrepared){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public void initDataSorce(String url) {
        if (url==null || url.isEmpty() ){
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
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
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
            if (ObjectUtils.isNotNull(musicStateChangeListener)){
                musicStateChangeListener.onMusicStart();
            }

            new UpDateMusicPositionTask().execute();
        }else {
            initDataSorce(mMusicPlayList.getCurMusic().getDownloadLink());
        }

    }

    @Override
    public void stop() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            if(ObjectUtils.isNotNull(musicStateChangeListener)){
                musicStateChangeListener.onMusicStop();
            }
        }

    }

    @Override
    public void nextSong() {
        switch (playMode){
            case "play_mode_one":
                start();
                break;
            case "play_mode_loop":
                mediaPlayer.reset();
                initMediaPlayer();
                //Log.d(TAG,mMusicPlayList.getNextMusic().getTitle());
                initDataSorce(mMusicPlayList.getNextMusic().getDownloadLink());
                if (ObjectUtils.isNotNull(musicChangeListener)){
                    musicChangeListener.onMusicChange(mMusicPlayList.getCurrentIndex());
                }
                break;
            case "play_mode_shuffle":
                randomPlay();
                break;
        }

    }

    @Override
    public void lastSong() {
        switch (playMode){
            case "play_mode_one":
                start();
                break;
            case "play_mode_loop":
                mediaPlayer.reset();
                initMediaPlayer();
                initDataSorce(mMusicPlayList.getLastMusic().getDownloadLink());
                if(ObjectUtils.isNotNull(musicChangeListener)){
                    musicChangeListener.onMusicChange(mMusicPlayList.getCurrentIndex());
                }
                break;
            case "play_mode_shuffle":
                randomPlay();
                break;
        }

    }

    /*
    * 随机播放
    * */
    private void randomPlay(){
        int max=mMusicPlayList.getList().size()-1;
        int min=0;
        Random random = new Random();
        while (true){
            int randomIndex = random.nextInt(max)%(max-min+1) + min;

            if (randomIndex!=mMusicPlayList.getCurrentIndex()){
                mMusicPlayList.setCurrentIndex(randomIndex);
                mediaPlayer.reset();
                initMediaPlayer();
                initDataSorce(mMusicPlayList.getCurMusic().getDownloadLink());
                if (ObjectUtils.isNotNull(musicChangeListener)){
                    musicChangeListener.onMusicChange(mMusicPlayList.getCurrentIndex());
                }
                break;
            }
        }




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
        SPUtils.saveString(Constants.SP_KEY_PLAY_MODE,mode);
        playMode = mode;

    }

    @Override
    public void setOnMusicStartAndStopListener(OnMusicStateChangeListener listener) {
        this.musicStateChangeListener = listener;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        nextSong();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return true;
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
