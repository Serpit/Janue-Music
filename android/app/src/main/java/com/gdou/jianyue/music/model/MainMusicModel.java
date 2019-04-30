package com.gdou.jianyue.music.model;


import android.util.Log;
import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.bean.SongResultBean;
import com.gdou.jianyue.music.constraint.MainMusicContract;
import com.gdou.jianyue.net.NetClient;
import com.gdou.jianyue.net.api.ApiService;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.FileUtils;
import java.io.File;
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
    public Observable<SongResultBean> savePlayMusicLink(long songId) {
      return NetClient.getInstance().createService(ApiService.class).getSongMsg(Constants.METHOD_GET_MUSIC_INFO,""+songId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<PlayingMusic> loadPlayingMusicInfo(long songId){
       return Observable.just(DatabaseUtils.queryPlayingMusicById(songId));
    }

}
