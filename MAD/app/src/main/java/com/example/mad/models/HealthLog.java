package com.example.mad.models;

public class HealthLog {
    private String activityName;
    private String date;
    private String notes;
    private boolean isDone;

    public HealthLog(String activityName, String date, String notes, boolean isDone) {
        this.activityName = activityName;
        this.date = date;
        this.notes = notes;
        this.isDone = isDone;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}

