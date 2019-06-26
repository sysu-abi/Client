package com.sysu.ceres.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class UserList {
    private List<User> userList;
    private String state;

    private User user;
    private String status;

    public User getUser() { return user; }
    public void setUser(User value) { this.user = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public List<User> getUserList() { return userList; }
    public void setUserList(List<User> value) { this.userList = value; }

    public String getState() { return state; }
    public void setState(String value) { this.state = value; }

    public boolean isJointUser(Long uid) {
        for (User u : userList) {
            if(u.getUid().equals(uid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        final String json = gson.toJson(this);
        return json;
    }
}
