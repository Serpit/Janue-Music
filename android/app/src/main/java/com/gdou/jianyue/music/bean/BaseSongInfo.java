package com.gdou.jianyue.music.bean;

/*
* 歌曲最基本的显示信息
* */
public class BaseSongInfo {
    private long songid;
    private String songname;
    private String artist;
    private String piclink;
    public long getSongid() {
        return songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public String getPicLink() {
        return piclink;
    }

    public void setPicLink(String picLink) {
        this.piclink = picLink;
    }
}
