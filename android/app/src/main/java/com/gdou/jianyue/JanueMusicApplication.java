package com.gdou.jianyue;

import android.content.Context;

import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.greendao.DaoMaster;
import com.gdou.jianyue.greendao.DaoSession;
import com.gdou.jianyue.music.MusicPlayList;
import com.gdou.jianyue.music.service.MusicService;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.SPUtils;

import org.greenrobot.greendao.database.Database;

import io.flutter.app.FlutterApplication;

public class JanueMusicApplication extends FlutterApplication {
    private static Context context;
    private MusicService.MusicBinder musicBinder;
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SPUtils.saveString(Constants.SP_KEY_PLAY_MODE,Constants.PLAY_MODE_LOOP);
        initGreenDao(context);
        initMusicPlayList();
    }


    public static Context getContext(){
        return context;
    }


    public MusicService.MusicBinder getMusicBinder() {
        return musicBinder;
    }

    public void setMusicBinder(MusicService.MusicBinder musicBinder) {
        this.musicBinder = musicBinder;
    }


    private void initMusicPlayList(){
        int curIndex =  SPUtils.getInt(Constants.SP_KEY_CURRENT_INDEX);
        MusicPlayList.getInstance().init(curIndex);
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
    private void initGreenDao(Context context){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DatabaseUtils.DATABASE_NAME);
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
