package com.gdou.jianyue.music.presenter;

import android.app.Activity;
import android.util.Log;

import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.constraint.MainMusicContract;
import com.gdou.jianyue.music.model.MainMusicModel;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainMusicPresenter implements MainMusicContract.Presenter {
    private MainMusicContract.View mView;
    private MainMusicContract.Model mModel;
    public MainMusicPresenter() {
       mModel = new MainMusicModel();
    }

    @Override
    public void attachView(MainMusicContract.View view){
        this.mView = view;
    }

    @Override
    public void downloadLrcFile(String url,String musicName) {
       Disposable disposable = mModel.loadLrcFile(url,musicName).subscribe(new Consumer<File>() {
           @Override
           public void accept(File file) throws Exception {

               if (file.exists()){
                   mView.onLrcLoad(file);
               }

           }

       }, new Consumer<Throwable>() {
           @Override
           public void accept(Throwable throwable) throws Exception {
               Log.d("MainMusicPresenter",throwable.getMessage());
           }
       });
    }




    @Override
    public void loadPlayingMusicInfo(long songId) {
        Disposable disposable =  mModel.loadPlayingMusicInfo(songId).subscribe(playingMusic -> {
            mView.onMusicInfoLoad(playingMusic);
        });
    }
}
