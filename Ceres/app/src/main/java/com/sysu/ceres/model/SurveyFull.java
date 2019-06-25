package com.sysu.ceres.model;

import java.util.List;

public class SurveyFull {
    private Survey survey;
    private List<QuestionList> questionList;
    private String status;

    public Survey getSurvey() { return survey; }
    public void setSurvey(Survey value) { this.survey = value; }

    public List<QuestionList> getQuestionList() { return questionList; }
    public void setQuestionList(List<QuestionList> value) { this.questionList = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}