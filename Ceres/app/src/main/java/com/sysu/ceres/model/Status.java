package com.sysu.ceres.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Status {
    private String log;
    private String status;
    private User user;

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
