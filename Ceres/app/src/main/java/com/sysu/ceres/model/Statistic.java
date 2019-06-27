package com.sysu.ceres.model;

import java.io.Serializable;

public class Statistic {
    private long sid;
    private long qid;
    private long countA;
    private long countB;
    private long countD;
    private long countC;

    public long getSid() { return sid; }
    public void setSid(long value) { this.sid = value; }

    public long getQid() { return qid; }
    public void setQid(long value) { this.qid = value; }

    public long getCountA() { return countA; }
    public void setCountA(long value) { this.countA = value; }

    public long getCountB() { return countB; }
    public void setCountB(long value) { this.countB = value; }

    public long getCountD() { return countD; }
    public void setCountD(long value) { this.countD = value; }

    public long getCountC() { return countC; }
    public void setCountC(long value) { this.countC = value; }
}
