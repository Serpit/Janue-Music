package com.gdou.jianyue.databasetable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true)
    private long userId;
    private String userName;
    private String password;
    @Generated(hash = 1489414491)
    public User(long userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



}
