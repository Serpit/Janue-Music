package com.gdou.jianyue.event;

public class MusicEvent {
    private int position;

    public MusicEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
