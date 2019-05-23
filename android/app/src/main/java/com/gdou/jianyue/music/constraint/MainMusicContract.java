package com.gdou.jianyue.music.constraint;

import android.content.Context;

import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.bean.SongResultBean;
import com.gdou.jianyue.net.bean.BaseResponse;

import java.io.File;

import io.reactivex.Observable;

/*
* 主要播放界面的约束
* */

public interface MainMusicContract {
    interface View {
        void onLrcLoad(File file);
        void onMusicInfoLoad(PlayingMusic music);
        void onIsNotLogin();
        void onError(String msg);
        void onSuccess(String msg);
        void onDownloadSuccess();
        void onDownlaodFaild(String msg);
        void onIsCollect(boolean isCollect);
        void onRefuseDownload();
    }

    interface Model {
        Observable<File> loadLrcFile(String lrcUrl, String muiscname);
        Observable<PlayingMusic> loadPlayingMusicInfo(long songId);
        Observable<SongResultBean> savePlayMusicLink(long songId);
        Observable<BaseResponse> collectMusic(PlayingMusic playingMusic);
        Observable<BaseResponse> cancelCollectMusic(long songId);
        Observable<Boolean> downloadMusic(long songid);
    }

    interface Presenter {
        void attachView(View view);
        void downloadLrcFile(String url,String musicName);
        void loadPlayingMusicInfo(long songId);
        void collectMusic(long songId);
        void cancelCollectMusic(long songId);
        void queryIsCollect(long songid);
        void downloadMusic(long songId);
        void share(String msg);
    }

}
