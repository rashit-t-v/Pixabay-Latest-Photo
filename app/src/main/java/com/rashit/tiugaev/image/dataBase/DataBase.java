package com.rashit.tiugaev.image.dataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorits")
public class DataBase {
    @PrimaryKey()
    private int id;
    private String web;
    private String user;
    private String tag;

    public DataBase(int id, String web, String user, String tag) {
        this.id = id;
        this.web = web;
        this.user = user;
        this.tag = tag;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
