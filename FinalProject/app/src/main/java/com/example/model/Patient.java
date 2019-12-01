package com.example.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Patient extends Person {
    private boolean lastRate=false;
    private String lastClinicName=null;
    private String lastClinicAddress=null;
    private String appointmentTime = null; //should have the date and time !

    public Patient(String name, String pass, String email, Role role){
        super(name, pass, email, role);
    }

    public Patient(){
        super(null,"abc",null,Role.Patient);
    }

    public boolean getLastRate(){return lastRate;}
    public void setLastRate(boolean rate){this.lastRate=rate;}
    public String getLastClinicName(){return lastClinicName;}
    public void setLastClinicName(String lastClinicName){this.lastClinicName=lastClinicName;}
    public String getLastClinicAddress(){return  lastClinicAddress;}
    public void setLastClinicAddress(String lastClinicAddress) {
        this.lastClinicAddress = lastClinicAddress;
    }
    public String getAppointmentTime(){return appointmentTime;}
    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }


    //override Map
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.getName());
        result.put("password", this.getPassword());
        result.put("email", this.getEmail());
        result.put("role", this.getRole());
        result.put("lastRate",lastRate);
        result.put("lastClinicName",lastClinicName);
        result.put("lastClinicAddress",lastClinicAddress);
        result.put("appointmentTime", appointmentTime);

        return result;
    }


    public boolean isItTimeToRate(){
        //appointment time should have date and time!
        //get current time
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            String[] anApptParts = appointmentTime.split("#");
            LocalDate date = LocalDate.parse(anApptParts[0],formatter);
            LocalTime time = LocalTime.parse(anApptParts[1]);

            LocalTime nowTime = LocalTime.now();
            LocalDate nowDate = LocalDate.now();
            System.err.println(lastRate);
            System.err.println((nowDate.isEqual(date)&&( nowTime.isAfter(time)|| nowTime.equals(time)) ));
            if( ( (nowDate.isEqual(date)&&( nowTime.isAfter(time)|| nowTime.equals(time)) )  ||   nowDate.isAfter(date)     ) && lastRate){
                lastRate =false;
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            System.err.println("Patient IsitTimeToRate Error");
            System.err.println(e.getMessage());
            return false;
        }

    }
}
