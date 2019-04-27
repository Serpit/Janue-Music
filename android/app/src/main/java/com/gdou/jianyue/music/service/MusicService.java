package com.gdou.jianyue.music.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.gdou.jianyue.music.controller.MusicController;
import com.gdou.jianyue.music.controller.MusicControllerImpl;

public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    private MusicBinder mBinder = new MusicBinder();
    private MusicController controller = MusicControllerImpl.getInstance();
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    private void startMusic(){
        Log.d(TAG,"--------STARTMUSIC--------");
        controller.start();
    }


    private void stopMusic(){
        Log.d(TAG,"--------STOPMUSIC--------");
        controller.stop();
    }

    private void nextMusic(){
        controller.nextSong();
    }

    private void switchMusic(long songid){
        controller.switchMusic(songid);
    }

    private void lastMusic(){
        controller.lastSong();
    }
    public class MusicBinder extends Binder {
        public void startMusic(){
            MusicService.this.startMusic();
        }

        public void stopMusic(){
            MusicService.this.stopMusic();
        }


        public void nextMusic(){
            MusicService.this.nextMusic();
        }

        public void lastMusic(){
            MusicService.this.lastMusic();
        }

        public void switchMusic(long songid){
            MusicService.this.switchMusic(songid);
        }
    }

}
