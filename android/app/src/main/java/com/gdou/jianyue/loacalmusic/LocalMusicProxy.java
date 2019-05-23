package com.gdou.jianyue.loacalmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.bean.BaseSongInfo;
import com.gdou.jianyue.music.controller.MusicController;
import com.gdou.jianyue.music.controller.MusicControllerImpl;
import com.gdou.jianyue.nativeplugin.StartActivityPlugin;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


//本地音乐的操作
public class LocalMusicProxy {
    private LocalMusicProxy() {
    }
    private static LocalMusicProxy instance = new LocalMusicProxy();

    private static final String TAG = LocalMusicProxy.class.getSimpleName();

    public static  LocalMusicProxy getInstance(){
        if (ObjectUtils.isNotNull(instance)){
            return instance;
        }  else {

            return new LocalMusicProxy();
        }
    }
    public List<BaseSongInfo> getLocalMusicList(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,
                null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<BaseSongInfo> baseSongInfoList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            for (int i = 0; i < cursor.getCount(); i++) {
                BaseSongInfo info = new BaseSongInfo();

                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                /*long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));*/
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String ablum = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                if (ismusic==1){
                    info.setSongid(id);
                    info.setSongname(title);
                    info.setArtist(artist);
                    info.setPlaylink(url);
                    info.setAblum(ablum);
                    baseSongInfoList.add(info);
                }
                //Log.d(TAG,"title: "+title+"artist  :"+artist+"isMusic: "+ismusic+"url: "+url+"ablum: "+ablum);
                cursor.moveToNext();
            }
        }
        return baseSongInfoList;
    }

    public  void playLocalMusic(Context context){
        //先进行删除操作
        DatabaseUtils.deleteAllPlayingMusic();
        //再将本地的音乐全部入库
        List<BaseSongInfo> list =  getInstance().getLocalMusicList(context);
        List<PlayingMusic> playingMusicList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            PlayingMusic playingMusic = new PlayingMusic();
            playingMusic.setAblum(list.get(i).getAblum());
            playingMusic.setDownloadLink(list.get(i).getPlaylink());
            playingMusic.setAuthor(list.get(i).getArtist());
            playingMusic.setTitle(list.get(i).getSongname());
            playingMusic.setSongId(list.get(i).getSongid());
            playingMusic.setLrcLink("");
            playingMusic.setPicLink("");
            playingMusicList.add(playingMusic);
        }
        DatabaseUtils.insertPlayingMusic(playingMusicList);
        MusicPlayList.getInstance().updateList();
        //重置curIndex
        MusicPlayList.getInstance().setCurrentIndex(0);
    }

}
