package com.gdou.jianyue.music.presenter;

import android.app.Activity;
import android.util.Log;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.constraint.MainMusicContract;
import com.gdou.jianyue.music.model.MainMusicModel;
import com.gdou.jianyue.net.bean.BaseResponse;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.NetUtils;
import com.gdou.jianyue.utils.SPUtils;

import java.io.File;

import io.reactivex.Observable;
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
    public void queryIsCollect(long songid) {
        Disposable disposable =  Observable.just(DatabaseUtils.queryIsCollect(songid,Long.parseLong(SPUtils.getString(Constants.SP_kEY_USER_ID)))).subscribe(isCollect->{
            mView.onIsCollect(isCollect);
        });
    }

    @Override
    public void loadPlayingMusicInfo(long songId) {
        Disposable disposable =  mModel.loadPlayingMusicInfo(songId).subscribe(playingMusic -> {
            mView.onMusicInfoLoad(playingMusic);
        });
    }

    @Override
    public void collectMusic(long songId) {
        boolean isLogin = SPUtils.getBoolean(Constants.SP_KEY_IS_LOGIN);
        if (isLogin){
            Disposable disposable =  mModel.collectMusic(MusicPlayList.getInstance().getCurMusic()).subscribe(new Consumer<BaseResponse>() {
                @Override
                public void accept(BaseResponse baseResponse) throws Exception {
                    if (baseResponse.getState().equals("success")) {
                        mView.onSuccess(baseResponse.getMsg());
                    } else {
                        mView.onError(baseResponse.getMsg());
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.onError(throwable.getMessage());
                }
            });
        }else {
            mView.onIsNotLogin();
        }
    }

    @Override
    public void cancelCollectMusic(long songId) {
        boolean isLogin = SPUtils.getBoolean(Constants.SP_KEY_IS_LOGIN);
        if (isLogin){
            Disposable disposable =  mModel.cancelCollectMusic(songId).subscribe(baseResponse -> {
                if (baseResponse.getState().equals("success")){
                    mView.onSuccess(baseResponse.getMsg());
                }else {
                    mView.onError(baseResponse.getMsg());
                }
            });
        }else {
            mView.onIsNotLogin();
        }
    }


    @Override
    public void downloadMusic(long songId) {
        if (SPUtils.getBoolean(Constants.SP_kEY_NET_DOWNLOAD) && !NetUtils.isWifiConnected((Activity)mView)){
            mView.onRefuseDownload();
            return;
        }
        Disposable disposable = mModel.downloadMusic(songId).subscribe(isSuccess->{
           if (!isSuccess) {
               mView.onDownlaodFaild("下载失败！");
           }
        },throwable ->{
            mView.onDownlaodFaild(throwable.getMessage());
        });
    }
}
