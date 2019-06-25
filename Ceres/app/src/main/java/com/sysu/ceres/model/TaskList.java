package com.sysu.ceres.model;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> taskList = new ArrayList<>();

    public List<Task> getTaskList() { return taskList; }
    public void setTaskList(List<Task> value) { this.taskList = value; }
}
