package com.gdou.jianyue.databasetable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DownloadMusic {
    @Id
    private long  songId;

    private String title;
    private String author;
    private String path;
    private String ablum;
    @Generated(hash = 1267526352)
    public DownloadMusic(long songId, String title, String author, String path,
            String ablum) {
        this.songId = songId;
        this.title = title;
        this.author = author;
        this.path = path;
        this.ablum = ablum;
    }
    @Generated(hash = 1439064721)
    public DownloadMusic() {
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
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getAblum() {
        return this.ablum;
    }
    public void setAblum(String ablum) {
        this.ablum = ablum;
    }

    

  
}
