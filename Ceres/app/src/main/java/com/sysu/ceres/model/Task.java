package com.sysu.ceres.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class Task implements Serializable {
    @SerializedName("tid")
    private Long tid;
    @SerializedName("uid")
    private Long uid;
    @SerializedName("title")
    private String title;
    @SerializedName("detail")
    private String detail;
    @SerializedName("money")
    private Long money;
    @SerializedName("type")
    private String type;
    @SerializedName("total_num")
    private Long totalNum;
    @SerializedName("current_num")
    private Long currentNum;
    @SerializedName("start_time")
    private Long startTime;
    @SerializedName("end_time")
    private Long endTime;
    @SerializedName("state")
    private String state;

    public Task(Long tid, Long uid, String title, String detail, Long money, String type,
                Long totalNum, Long currentNum, Long startTime, Long endTime, String state) {
        this.tid = tid;
        this.uid = uid;
        this.title = title;
        this.detail = detail;
        this.money = money;
        this.type = type;
        this.totalNum = totalNum;
        this.currentNum = currentNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
    }

    public Long getTid() { return tid; }
    public void setTid(Long value) { this.tid = value; }

    public Long getUid() { return uid; }
    public void setUid(Long value) { this.uid = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getDetail() { return detail; }
    public void setDetail(String value) { this.detail = value; }

    public Long getMoney() { return money; }
    public void setMoney(Long value) { this.money = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public Long getTotalNum() { return totalNum; }
    public void setTotalNum(Long value) { this.totalNum = value; }

    public Long getCurrentNum() { return currentNum; }
    public void setCurrentNum(Long value) { this.currentNum = value; }

    public Long getStartTime() { return startTime; }
    public void setStartTime(Long value) { this.startTime = value; }

    public Long getEndTime() { return endTime; }
    public void setEndTime(Long value) { this.endTime = value; }

    public String getState() { return state; }
    public void setState(String value) { this.state = value; }

    @Override
    public String toString() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(this);
        return json;
    }
}