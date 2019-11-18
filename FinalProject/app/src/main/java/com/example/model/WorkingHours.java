package com.example.model;

public class WorkingHours {
    private String startTime;
    private String endTime;
    private boolean closed;

    public WorkingHours(){}

    public WorkingHours(String startTime, String endTime, boolean closed){
        this.startTime=startTime;
        this.endTime=endTime;
        this.closed=closed;
    }

    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public boolean getClosed(){
        return closed;
    }
    public void setStartTime(String startTime){
        this.startTime=startTime;
    }
    public void setEndTime(String endTime){
        this.endTime=endTime;
    }
    public void setClosed(boolean closed){
        this.closed=closed;
    }
}
