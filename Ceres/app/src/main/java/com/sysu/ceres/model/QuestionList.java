package com.sysu.ceres.model;

public class QuestionList {
    private Long sid;
    private Long qid;
    private String qtype;
    private String qtitle;
    private String answerA;
    private String answerB;
    private String answerD;
    private String answerC;

    public Long getSid() { return sid; }
    public void setSid(Long value) { this.sid = value; }

    public Long getQid() { return qid; }
    public void setQid(Long value) { this.qid = value; }

    public String getQtype() { return qtype; }
    public void setQtype(String value) { this.qtype = value; }

    public String getQtitle() { return qtitle; }
    public void setQtitle(String value) { this.qtitle = value; }

    public String getAnswerA() { return answerA; }
    public void setAnswerA(String value) { this.answerA = value; }

    public String getAnswerB() { return answerB; }
    public void setAnswerB(String value) { this.answerB = value; }

    public String getAnswerD() { return answerD; }
    public void setAnswerD(String value) { this.answerD = value; }

    public String getAnswerC() { return answerC; }
    public void setAnswerC(String value) { this.answerC = value; }
}
