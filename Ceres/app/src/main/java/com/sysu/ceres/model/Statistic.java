package com.sysu.ceres.model;

import java.io.Serializable;

public class Statistic {
    private Long sid;
    private Long qid;
    private Long countA;
    private Long countB;
    private Long countD;
    private Long countC;

    public Long getSid() { return sid; }
    public void setSid(Long value) { this.sid = value; }

    public Long getQid() { return qid; }
    public void setQid(Long value) { this.qid = value; }

    public Long getCountA() { return countA; }
    public void setCountA(Long value) { this.countA = value; }

    public Long getCountB() { return countB; }
    public void setCountB(Long value) { this.countB = value; }

    public Long getCountD() { return countD; }
    public void setCountD(Long value) { this.countD = value; }

    public Long getCountC() { return countC; }
    public void setCountC(Long value) { this.countC = value; }
}
