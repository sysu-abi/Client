package com.sysu.ceres.model;

import java.util.List;

public class SurveyList {
    private List<Survey> survey;
    private String status;

    public List<Survey> getSurvey() { return survey; }
    public void setSurvey(List<Survey> value) { this.survey = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }
}
