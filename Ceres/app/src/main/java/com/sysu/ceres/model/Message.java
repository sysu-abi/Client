package com.sysu.ceres.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class Message implements Serializable {
    private Long tid;
    private Long uid;
    private Long time;
    private Long floor;
    private String detail;

    Message(Long tid, Long uid, Long time, Long floor, String detail) {
        this.tid = tid;
        this.uid = uid;
        this.time = time;
        this.floor = floor;
        this.detail = detail;
    }

    public Long getTid() { return tid; }
    public void setTid(Long value) { this.tid = value; }

    public Long getUid() { return uid; }
    public void setUid(Long value) { this.uid = value; }

    public Long getTime() { return time; }
    public void setTime(Long value) { this.time = value; }

    public Long getFloor() { return floor; }
    public void setFloor(Long value) { this.floor = value; }

    public String getDetail() { return detail; }
    public void setDetail(String value) { this.detail = value; }

    @Override
    public String toString() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(this);
        return json;
    }
}
