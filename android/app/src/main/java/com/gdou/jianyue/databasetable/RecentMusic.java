package com.gdou.jianyue.databasetable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class RecentMusic {
    @Id
    private long songId;

    private String title;

    private String downloadLink;
    private String picLink;
    private String author;
    private String ablum;
    private String lrcLink;
    @Generated(hash = 1036901664)
    public RecentMusic(long songId, String title, String downloadLink,
            String picLink, String author, String ablum, String lrcLink) {
        this.songId = songId;
        this.title = title;
        this.downloadLink = downloadLink;
        this.picLink = picLink;
        this.author = author;
        this.ablum = ablum;
        this.lrcLink = lrcLink;
    }
    @Generated(hash = 1840180525)
    public RecentMusic() {
    }
    public long getSongId() {
        return this.songId;
    }
    public void setSongId(long songId) {
        this.songId = songId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDownloadLink() {
        return this.downloadLink;
    }
    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
    public String getPicLink() {
        return this.picLink;
    }
    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAblum() {
        return this.ablum;
    }
    public void setAblum(String ablum) {
        this.ablum = ablum;
    }
    public String getLrcLink() {
        return this.lrcLink;
    }
    public void setLrcLink(String lrcLink) {
        this.lrcLink = lrcLink;
    }
    

   
    
}
