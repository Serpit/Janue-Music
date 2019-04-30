package com.gdou.jianyue.loacalmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.gdou.jianyue.music.bean.BaseSongInfo;
import com.gdou.jianyue.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicProxy {
    private LocalMusicProxy() {
    }
    private static LocalMusicProxy instance;
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

                int ismusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                if (ismusic==1){
                    info.setSongid(id);
                    info.setSongname(title);
                    info.setArtist(artist);
                    info.setPicLink(url);
                    baseSongInfoList.add(info);
                }
               // Log.d(TAG,"title: "+title+"artist  :"+artist+"isMusic: "+ismusic+"url: "+url+"size: "+size+"duration: "+ duration);
                cursor.moveToNext();
            }
        }
        return baseSongInfoList;
    }
}
