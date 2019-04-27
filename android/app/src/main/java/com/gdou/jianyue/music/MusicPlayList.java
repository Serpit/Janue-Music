package com.gdou.jianyue.music;



import com.gdou.jianyue.Constants.Constants;
import com.gdou.jianyue.databasetable.PlayingMusic;
import com.gdou.jianyue.utils.DatabaseUtils;
import com.gdou.jianyue.utils.ListUtils;
import com.gdou.jianyue.utils.ObjectUtils;
import com.gdou.jianyue.utils.SPUtils;

import java.util.List;

public class MusicPlayList {
    private List<PlayingMusic> list ;
    private  int currentIndex;
    private static MusicPlayList instance = new MusicPlayList();
    public static MusicPlayList getInstance(){
        if (ObjectUtils.isNotNull(instance)){
            return instance;
        }else {
            return new MusicPlayList();
        }

    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }


    public void init(int currentIndex){
        list =  DatabaseUtils.queryAllPlayingMusic();
    }

    private MusicPlayList() {

    }

    public List<PlayingMusic> getList() {
        return list;
    }

    public void updateList(){
        list.addAll(DatabaseUtils.queryAllPlayingMusic());
    }


    public PlayingMusic getMusciBySongId(long id){
        for (PlayingMusic music : list) {
            if (music.getSongId()==id){
                return music;
            }
        }
        return null;
    }



    public int getMusicIndexBySongId(long id){
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSongId()==id){
                return i;
            }
        }

        return 0;
    }

    public PlayingMusic getLastMusic(){
        if (ObjectUtils.isNull(list) || ListUtils.isEmpty(list)) {
            return null;
        }
        currentIndex = (currentIndex -1)>=0 ? currentIndex -1: 0;
        return list.get(currentIndex);
    }

    public PlayingMusic getNextMusic(){
        if (ObjectUtils.isNull(list) || ListUtils.isEmpty(list)) {
            return null;
        }
        currentIndex = currentIndex +1>=list.size()-1? list.size()-1 : currentIndex +1 ;
        return list.get(currentIndex);

    }

    public PlayingMusic getCurMusic(){
        if (ObjectUtils.isNull(list) || ListUtils.isEmpty(list)) {
            return null;
        }
        return list.get(currentIndex);
    }

    public  void saveCurIndex(){
        SPUtils.saveInt(Constants.SP_KEY_CURRENT_INDEX,currentIndex);
    }



}
