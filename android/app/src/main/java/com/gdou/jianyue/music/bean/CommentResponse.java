package com.gdou.jianyue.music.bean;

import java.util.List;

public class CommentResponse {
    private String state;
    private String msg;
    private List<Comment> data;

    public CommentResponse() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }
}
