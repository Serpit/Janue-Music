package com.gdou.jianyue.proxy;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.JanueMusicApplication;
import com.gdou.jianyue.databasetable.CollectionMusic;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.databasetable.RecentMusic;
import com.gdou.jianyue.loacalmusic.LocalMusicProxy;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.bean.BaseSongInfo;
import com.gdou.jianyue.music.bean.SongResultBean;
import com.gdou.jianyue.music.controller.MusicController;
import com.gdou.jianyue.music.controller.MusicControllerImpl;
import com.gdou.jianyue.music.model.MainMusicModel;
import com.gdou.jianyue.music.service.MusicService;
import com.gdou.jianyue.music.view.MusicPlayerActivity;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.SPUtils;
import com.gdou.jianyue.utils.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MethodProxy {
    private static final String TAG= MethodProxy.class.getSimpleName();
    public static void startMusicActivity(Activity flutterActivity, Bundle bundle){
        Intent intent = new Intent(flutterActivity,MusicPlayerActivity.class);
        intent.putExtras(bundle);
        flutterActivity.startActivity(intent);

    }


    public static void startMusicActivityWithoutInfo(Activity flutterActivity, Bundle bundle){
        MainMusicModel model = new MainMusicModel();
        Disposable disposable = model.savePlayMusicLink(bundle.getLong(Constants.SONG_ID)).subscribe(new Consumer<SongResultBean>() {
           @Override
           public void accept(SongResultBean songResultBean) throws Exception {
               PlayingMusic music  = new PlayingMusic();
               music.setSongId(bundle.getLong(Constants.SONG_ID));
               music.setAuthor(bundle.getString(Constants.ARTISTS));
               //Log.d(TAG,songResultBean.toString());
               music.setAblum(songResultBean.getSongInfo().getAlbum_title());

               music.setLrcLink(songResultBean.getSongInfo().getLrclink());
               music.setPicLink(songResultBean.getSongInfo().getPic_premium());

               music.setTitle(TextUtils.removeExcess(songResultBean.getSongInfo().getTitle()));

               music.setDownloadLink(songResultBean.getBitrate().getFile_link());
               DatabaseUtils.insertPlayingMusic(music);
               MusicPlayList.getInstance().updateList();
               startMusicActivity(flutterActivity,bundle);
           }
       });


    }

    public static void addIntoPlayingList(MethodCall methodCall){
        PlayingMusic music  = new PlayingMusic();
        music.setSongId(Long.parseLong(methodCall.argument("song_id")));
        music.setAblum(methodCall.argument("album_title"));
        music.setAuthor(methodCall.argument("author"));
        music.setLrcLink(methodCall.argument("lrclink"));
        music.setPicLink(methodCall.argument("pic_huge"));
        music.setTitle(TextUtils.removeExcess(methodCall.argument("title")));

        DatabaseUtils.insertPlayingMusic(music);
        MusicPlayList.getInstance().updateList();
    }


    public static void queryAllRecentMusic( MethodChannel.Result result){
       List<RecentMusic> recentMusicList =  DatabaseUtils.queryAllRecentMusic();
       List<BaseSongInfo> songInfoList = new ArrayList<>(recentMusicList.size());
       for (int i = 0; i < recentMusicList.size(); i++) {
            BaseSongInfo songInfo = new BaseSongInfo();
            RecentMusic recentMusic =  recentMusicList.get(i);
            songInfo.setSongid(recentMusic.getSongId());
            songInfo.setSongname(recentMusic.getTitle());
            songInfo.setArtist(recentMusic.getAuthor());
            songInfoList.add(songInfo);
       }

       Gson gson = new Gson();
       String jsonString = gson.toJson(songInfoList);
       result.success(jsonString);
    }

    public static void startMusicActivityFromBar(Activity flutterActivity){
        PlayingMusic playingMusic = MusicPlayList.getInstance().getCurMusic();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.SONG_ID,playingMusic.getSongId());
        bundle.putString(Constants.ARTISTS,playingMusic.getAuthor());
        bundle.putString(Constants.SONG_NAME,playingMusic.getTitle());
        startMusicActivity(flutterActivity,bundle);
    }


    public static void startMusic(Context context){
       MusicService.MusicBinder binder = ((JanueMusicApplication)((Activity)context).getApplication()).getMusicBinder();
       binder.startMusic();
    }

    public static void stopMusic(Context context){
        MusicService.MusicBinder binder = ((JanueMusicApplication)((Activity)context).getApplication()).getMusicBinder();
        binder.stopMusic();
    }

    public static void getIsPlaying(MethodChannel.Result result){
        MusicController musicController = MusicControllerImpl.getInstance();
        result.success(musicController.isPalying());
    }

    public static void getPlayingList(MethodChannel.Result result){
         List<PlayingMusic> playinglist =    MusicPlayList.getInstance().getList();
         List<BaseSongInfo> list = new ArrayList<>(playinglist.size());
         for (int i = 0; i < playinglist.size(); i++) {
            BaseSongInfo info = new BaseSongInfo();
            PlayingMusic music =  playinglist.get(i);
            info.setSongid(music.getSongId());
            info.setArtist(music.getAuthor());
            info.setSongname(music.getTitle());
            info.setPlaylink(music.getPicLink());
            list.add(info);
         }
         Gson gson = new Gson();
         String json = gson.toJson(list);
         result.success(json);
    }

    public static void getLocalMusicList(Context context,MethodChannel.Result result){
        List<BaseSongInfo> list =   LocalMusicProxy.getInstance().getLocalMusicList(context);
        Gson gson = new Gson();
        result.success(gson.toJson(list));
    }

    public static void switchMusic(MethodCall methodCall){

        long songid = Long.parseLong(methodCall.argument("songId"));
        MusicControllerImpl.getInstance().switchMusic(songid);
    }


    public static void getCurIndex(MethodChannel.Result result){
       // Log.d(TAG,MusicPlayList.getInstance().getCurrentIndex()+"");
        result.success(MusicPlayList.getInstance().getCurrentIndex());
    }


    public static void saveUserInfo(MethodCall methodCall){
        SPUtils.saveString(Constants.SP_kEY_USER_ID, methodCall.argument("userid"));
        SPUtils.saveBoolean(Constants.SP_KEY_IS_LOGIN,true);
        SPUtils.saveString(Constants.SP_KEY_USER_NAME,methodCall.argument("username"));
    }

    public static void getUserIsLogin(MethodChannel.Result result){
        Map<String,Object> map = new HashMap<>();
        map.put(Constants.SP_KEY_IS_LOGIN,SPUtils.getBoolean(Constants.SP_KEY_IS_LOGIN));
        map.put(Constants.SP_kEY_USER_ID,SPUtils.getString(Constants.SP_kEY_USER_ID));
        map.put(Constants.SP_KEY_USER_NAME,SPUtils.getString(Constants.SP_KEY_USER_NAME));
        result.success(map);
    }

    public static void logout(){
        SPUtils.saveBoolean(Constants.SP_KEY_IS_LOGIN,false);
    }

    public static void getUserId(MethodChannel.Result result){
        Map<String ,String> map = new HashMap<>();
        map.put(Constants.SP_kEY_USER_ID,SPUtils.getString(Constants.SP_kEY_USER_ID));
        result.success(map);
    }

    public static void insertCollection(MethodCall methodCall){
       //List<CollectionMusic> musics = (List<CollectionMusic>) methodCall.argument("collection");
       Log.d(TAG,methodCall.argument("collection"));
       String json = methodCall.argument("collection");
        Gson gson = new Gson();

        List<CollectionMusic> musics  = gson.fromJson(json,new TypeToken<List<CollectionMusic>>(){}.getType());
       DatabaseUtils.insertAllCollection(musics);
    }

    public static void getSettingInfo(MethodChannel.Result result){
        Map<String,Boolean> map = new HashMap<>();
        map.put(Constants.SP_kEY_NET_PLAY,SPUtils.getBoolean(Constants.SP_kEY_NET_PLAY));
        map.put(Constants.SP_kEY_NET_DOWNLOAD,SPUtils.getBoolean(Constants.SP_kEY_NET_DOWNLOAD));
        result.success(map);
    }

    public static void setMobilePlayEnable(MethodCall methodCall){
        Boolean value = methodCall.argument(Constants.SP_kEY_NET_PLAY);
        SPUtils.saveBoolean(Constants.SP_kEY_NET_PLAY,value);
    }
    public static void setMobileDownloadEnable(MethodCall methodCall){
        Boolean value = methodCall.argument(Constants.SP_kEY_NET_DOWNLOAD);
        SPUtils.saveBoolean(Constants.SP_kEY_NET_DOWNLOAD,value);
    }

    public static void playLocalMusic(MethodCall methodCall,Context context){
        String  songid = methodCall.argument("songid");
        LocalMusicProxy.getInstance().playLocalMusic(Long.parseLong(songid),context);
    }
}
