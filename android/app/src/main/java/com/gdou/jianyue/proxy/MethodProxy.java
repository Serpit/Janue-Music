package com.gdou.jianyue.proxy;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.view.MusicPlayerActivity;
import com.gdou.jianyue.utils.DatabaseUtils;

import io.flutter.plugin.common.MethodCall;

public class MethodProxy {
    private static final String TAG= MethodProxy.class.getSimpleName();
    public static void startMusicActivity(Activity flutterActivity, Bundle bundle){
        Intent intent = new Intent(flutterActivity,MusicPlayerActivity.class);
        intent.putExtras(bundle);
        flutterActivity.startActivity(intent);

    }


    public static void addIntoPlayingList(MethodCall methodCall){
        PlayingMusic music  = new PlayingMusic();
        music.setSongId(Long.parseLong(methodCall.argument("song_id")));
        music.setAblum(methodCall.argument("album_title"));
        music.setAuthor(methodCall.argument("author"));
        music.setLrcLink(methodCall.argument("lrclink"));
        music.setPicLink(methodCall.argument("pic_huge"));
        music.setTitle(methodCall.argument("title"));

        DatabaseUtils.insertPlayingMusic(music);
        MusicPlayList.getInstance().updateList();
    }
}
