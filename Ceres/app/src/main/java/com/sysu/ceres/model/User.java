package com.sysu.ceres.model;

import java.io.Serializable;

public class User implements Serializable {
    private Long uid;
    private String name;
    private String phone;
    private String email;
    private String password;
    private Long money;
    private Long credit;

    public Long getUid() { return uid; }
    public void setUid(Long value) { this.uid = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getPhone() { return phone; }
    public void setPhone(String value) { this.phone = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public Long getMoney() { return money; }
    public void setMoney(Long value) { this.money = value; }

    public Long getCredit() { return credit; }
    public void setCredit(Long value) { this.credit = value; }
}
