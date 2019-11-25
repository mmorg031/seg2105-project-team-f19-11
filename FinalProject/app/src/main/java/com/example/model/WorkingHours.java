package com.example.model;

import android.widget.Toast;

import com.example.finalproject.dev3.EditWorkingHours;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

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

    public boolean inRange(String time){
        try {
            LocalTime timeP = LocalTime.parse(time);
            LocalTime from = LocalTime.parse(startTime);
            LocalTime to = LocalTime.parse(endTime);

            if(from.isBefore(timeP) && timeP.isBefore(to)) {
                return true;
            }
            else
                return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public static boolean isCorrectTime(String time){
        try {
            LocalTime timeP = LocalTime.parse(time);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static boolean isCorrectTimeInput(String start, String end){
        boolean startCorrect=false;
        boolean endCorrect=false;
        boolean inRange=false;

        /*if(! start.matches("(?:[0-1][0-9]|2[0-4]):[0-5]\\d")){
            startCorrect=true;
        }
        if(! end.matches("(?:[0-1][0-9]|2[0-4]):[0-5]\\d")){
            endCorrect=true;
        }*/
        LocalTime from=null;
        LocalTime to=null;

        try {
            from = LocalTime.parse(start);
            startCorrect=true;
        }
        catch(Exception e){
            return false;
        }
        try {
            to = LocalTime.parse(end);
            endCorrect=true;
        }
        catch (Exception e){
            return  false;
        }


        if(from.isBefore(to)) {
            inRange = true;
        }


        System.err.println(startCorrect);
        System.err.println(endCorrect);
        System.err.println(inRange);
        return startCorrect && endCorrect && inRange;

    }
}
