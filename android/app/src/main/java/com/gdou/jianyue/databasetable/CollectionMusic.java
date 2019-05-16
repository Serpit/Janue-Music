package com.gdou.jianyue.databasetable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CollectionMusic {
    @Id(autoincrement = true)
    private long collectionid;
    private long songid;
    private String title;
    private String artist;
    private String downloadlink;
    private String piclink;
    private long userid;
    @Generated(hash = 748377740)
    public CollectionMusic(long collectionid, long songid, String title,
            String artist, String downloadlink, String piclink, long userid) {
        this.collectionid = collectionid;
        this.songid = songid;
        this.title = title;
        this.artist = artist;
        this.downloadlink = downloadlink;
        this.piclink = piclink;
        this.userid = userid;
    }
    @Generated(hash = 1345131847)
    public CollectionMusic() {
    }
    public long getCollectionid() {
        return this.collectionid;
    }
    public void setCollectionid(long collectionid) {
        this.collectionid = collectionid;
    }
    public long getSongid() {
        return this.songid;
    }
    public void setSongid(long songid) {
        this.songid = songid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getDownloadlink() {
        return this.downloadlink;
    }
    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }
    public String getPiclink() {
        return this.piclink;
    }
    public void setPiclink(String piclink) {
        this.piclink = piclink;
    }
    public long getUserid() {
        return this.userid;
    }
    public void setUserid(long userid) {
        this.userid = userid;
    }

}
