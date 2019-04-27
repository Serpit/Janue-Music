package com.gdou.jianyue.music.constraint;

import android.content.Context;

import com.gdou.jianyue.databasetable.PlayingMusic;

import java.io.File;

import io.reactivex.Observable;

/*
* 主要播放界面的约束
* */

public interface MainMusicContract {
    interface View {
        void onLrcLoad(File file);
        void onMusicInfoLoad(PlayingMusic music);

    }

    interface Model {
        Observable<File> loadLrcFile(String lrcUrl, String file);
        Observable<PlayingMusic> loadPlayingMusicInfo(long songId);
        Observable<String> savePlayMusicLink(long songId);
    }

    interface Presenter {
        void attachView(View view);
        void downloadLrcFile(String url,String musicName);
        void loadPlayingMusicInfo(long songId);

    }

}
