package com.mgame.dao.entity;

import java.sql.Timestamp;

public class UserInfoEntity {
    private String id;
    private String nickName;
    private String account;
    private String password;
    private Timestamp insertDt;
    private Timestamp updDt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(Timestamp insertDt) {
        this.insertDt = insertDt;
    }

    public Timestamp getUpdDt() {
        return updDt;
    }

    public void setUpdDt(Timestamp updDt) {
        this.updDt = updDt;
    }
}
