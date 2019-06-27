package com.sysu.ceres.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Status {
    private String cookie;
    private String log;
    private String status;
    private User user;
    private String state;

    public String getCookie() { return cookie; }
    public void setCookie(String value) { this.cookie = value; }

    public String getState() { return state; }
    public void setState(String value) { this.state = value; }

    public User getUser() { return user; }
    public void setUser(User value) { this.user = value; }

    public String getLog() { return log; }
    public void setLog(String value) { this.log = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    @Override
    public String toString() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(this);
        return json;
    }
}
