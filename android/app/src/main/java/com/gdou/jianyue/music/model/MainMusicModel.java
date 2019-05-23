package com.gdou.jianyue.music.model;


import android.os.Environment;
import android.util.Log;
import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.databasetable.DownloadMusic;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.bean.SongResultBean;
import com.gdou.jianyue.music.constraint.MainMusicContract;
import com.gdou.jianyue.net.NetClient;
import com.gdou.jianyue.net.api.ApiService;
import com.gdou.jianyue.net.bean.BaseResponse;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.FileUtils;
import com.gdou.jianyue.utils.SPUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class MainMusicModel implements MainMusicContract.Model {
    @Override
    public Observable<File> loadLrcFile(String lrcUrl, String musicName) {

      if(FileUtils.isLrcFileExists(musicName)){
          return Observable.just(FileUtils.getLrcFile(musicName));
      }else {
          return  NetClient.getInstance().createService(ApiService.class).downloadLrc(lrcUrl).subscribeOn(Schedulers.io())
                  .unsubscribeOn(Schedulers.io())
                  .map(new Function<ResponseBody, File>() {
                      @Override
                      public File apply(ResponseBody responseBody) throws Exception {
                          return FileUtils.saveLrcFile(responseBody.byteStream(),musicName);
                      }
                  })
                  .observeOn(AndroidSchedulers.mainThread());
      }


    }


    @Override
    public Observable<Boolean> downloadMusic(long songid) {
        PlayingMusic music = DatabaseUtils.queryPlayingMusicById(songid);
        String fileName = music.getAuthor() + " - " + music.getTitle()+".mp3";
        DownloadMusic downloadMusic = new DownloadMusic();
        downloadMusic.setSongId(music.getSongId());
        downloadMusic.setTitle(music.getTitle());
        downloadMusic.setAuthor(music.getAuthor());
        downloadMusic.setPath(Environment.getExternalStorageDirectory()+"/"+FileUtils.getRelativeMusicDir()+"/"+fileName);
        DatabaseUtils.insertDownloadMusic(downloadMusic);
        return NetClient.getInstance().downloadMusic(music.getDownloadLink(),fileName);
    }

    @Override
    public Observable<SongResultBean> savePlayMusicLink(long songId) {
      return NetClient.getInstance().createService(ApiService.class).getSongMsg(Constants.METHOD_GET_MUSIC_INFO,""+songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<PlayingMusic> loadPlayingMusicInfo(long songId){
       return Observable.just(DatabaseUtils.queryPlayingMusicById(songId));
    }

    @Override
    public Observable<BaseResponse> collectMusic(PlayingMusic playingMusic) {
        String userId = SPUtils.getString(Constants.SP_kEY_USER_ID);
        Map<String,String> map = new HashMap<>();
        map.put("songid",playingMusic.getSongId()+"");
        map.put("title",playingMusic.getTitle());
        map.put("artist",playingMusic.getAuthor());
        map.put("downloadlink",playingMusic.getDownloadLink());
        map.put("piclink",playingMusic.getPicLink());
        map.put("userid",userId);
        return NetClient.getInstance().createService(ApiService.class).collectMusic(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseResponse> cancelCollectMusic(long songId) {
        String userId = SPUtils.getString(Constants.SP_kEY_USER_ID);
        return NetClient.getInstance().createService(ApiService.class).cancelCollectMusic(songId,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
