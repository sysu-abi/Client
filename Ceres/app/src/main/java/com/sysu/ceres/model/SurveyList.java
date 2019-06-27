package com.sysu.ceres.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class SurveyList {
    private List<Survey> survey;
    private String status;

    public List<Survey> getSurvey() { return survey; }
    public void setSurvey(List<Survey> value) { this.survey = value; }

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
