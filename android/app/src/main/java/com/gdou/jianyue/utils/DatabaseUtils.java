package com.gdou.jianyue.utils;

import com.gdou.jianyue.JanueMusicApplication;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.databasetable.RecentMusic;
import com.gdou.jianyue.greendao.DaoSession;

import java.util.List;

public class DatabaseUtils {
    public static String DATABASE_NAME = "jian_yue_music_db";



    public static void inserUser(){
       // daoSession.insert()
    }
    public static void deleteUser(){

    }

    public static void inserMusicDownloadLink(long songid,String url){
        PlayingMusic playingMusic = JanueMusicApplication.getDaoSession().getPlayingMusicDao().load(songid);
        if(ObjectUtils.isNotNull(playingMusic)){
            playingMusic.setDownloadLink(url);
            JanueMusicApplication.getDaoSession().getPlayingMusicDao().update(playingMusic);
        }
    }

    public static void insertPlayingMusic(PlayingMusic playingMusic){

        if(ObjectUtils.isNull(JanueMusicApplication.getDaoSession().getPlayingMusicDao().load(playingMusic.getSongId()))){
            JanueMusicApplication.getDaoSession().getPlayingMusicDao().insert(playingMusic);
        }

    }


    public static void insertRecentMusic(RecentMusic recentMusic){
        JanueMusicApplication.getDaoSession().getRecentMusicDao().save(recentMusic);
    }



    public static void deletePlayingMusic(long songId){
        JanueMusicApplication.getDaoSession().getPlayingMusicDao().deleteByKey(songId);
    }


    public static void deleteRecentMusic(long songId){
        JanueMusicApplication.getDaoSession().getRecentMusicDao().deleteByKey(songId);
    }


    public static PlayingMusic queryPlayingMusicById(long songId){
        return JanueMusicApplication.getDaoSession().getPlayingMusicDao().loadByRowId(songId);
    }
    public static List<PlayingMusic> queryAllPlayingMusic(){
        return JanueMusicApplication.getDaoSession().getPlayingMusicDao().loadAll();
       // return  daoSession.getPlayingMusicDao().loadAll();
    }


    public static RecentMusic queryRecentMusicById(long songId){
        return  JanueMusicApplication.getDaoSession().getRecentMusicDao().load(songId);
    }

    public static List<RecentMusic> queryAllRecentMusic(){
        return JanueMusicApplication.getDaoSession().getRecentMusicDao().loadAll();
    }



}
