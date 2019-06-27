package com.sysu.ceres.model;

import java.util.List;

public class StatisticList {
    private String status;
    private List<Statistic> statistics;

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public List<Statistic> getStatistics() { return statistics; }
    public void setStatistics(List<Statistic> value) { this.statistics = value; }
}
