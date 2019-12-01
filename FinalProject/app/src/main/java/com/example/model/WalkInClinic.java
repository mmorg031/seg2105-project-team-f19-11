package com.example.model;

import android.widget.Toast;

import com.example.finalproject.activity_sign_in;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WalkInClinic {
    private String name;
    private String location;
    private String phone;
    private Map<String,Boolean> payments;
    private Map<String,Boolean> insurances;
    private Map<String,Role> services;
    private Map<String,WorkingHours> workingHours;
    private int numRated;
    private int sumRated;
    private Map<String, Integer> reviews;
    private Map<String, String> appointments;  //string is time appt booked, Patiet = email of Patient or maybe patient obj idk yet

    //public WalkInClinic(){}

    public WalkInClinic(){
        this.payments = new HashMap<String, Boolean>();
        for(PaymentMethods pm: PaymentMethods.values()){
            payments.put(pm.toString(),Boolean.FALSE);
        }
        this.insurances = new HashMap<String, Boolean>();
        for(Insurance in: Insurance.values()){
            insurances.put(in.toString(), Boolean.FALSE);
        }
    }

    public WalkInClinic(String name, String location, String phone){

        this.services = new HashMap<String, Role>();
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.payments = new HashMap<String, Boolean>();
        for(PaymentMethods pm: PaymentMethods.values()){
            payments.put(pm.toString(),Boolean.FALSE);
        }
        this.insurances = new HashMap<String, Boolean>();
        for(Insurance in: Insurance.values()){
            insurances.put(in.toString(), Boolean.FALSE);
        }
        this.services = new HashMap<String,Role>();
        services.put("None", Role.Employee);

        this.workingHours = new HashMap<String,WorkingHours>();
        workingHours.put("Sunday", new WorkingHours("8","16",true));
        workingHours.put("Monday", new WorkingHours("8","16",true) );
        workingHours.put("Tuesday", new WorkingHours("8","16",true));
        workingHours.put("Wednesday", new WorkingHours("8","16",true));
        workingHours.put("Thursday", new WorkingHours("8","16",true));
        workingHours.put("Friday", new WorkingHours("8","16",true));
        workingHours.put("Saturday", new WorkingHours("8","16",true));

        numRated = 5;
        sumRated = 25;
        this.reviews = new HashMap<String,Integer>();
        reviews.put("None",0);

        this.appointments = new HashMap<String,String>();
        //appointments.put("None", "None");

    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getLocation(){return location; }
    public void setLocation(String location){this.location=location;}
    public String getPhone() {return phone; }
    public void setPhone(String phone) {this.phone=phone;}
    public Map<String,Boolean> getPayments(){return payments;}
    public void setPayments(Map<String,Boolean> payments){this.payments=payments;}
    public Map<String,Boolean> getInsurances(){return insurances;}
    public void setInsurances(Map<String,Boolean> insurances){this.insurances=insurances;}
    public Map<String,Role> getServices(){return services;}
    public void setServices(Map<String,Role> services){this.services=services;}
    public Map<String,WorkingHours> getWorkingHours(){return workingHours;}
    public void setWorkingHours(Map<String,WorkingHours> workingHours){this.workingHours=workingHours;}
    public int getNumRated(){return numRated;}
    public void setNumRated(int numRated){this.numRated= numRated;}
    public int getSumRated(){return sumRated;}
    public void setSumRated(int sumRated){this.sumRated= sumRated;}
    public Map<String,Integer> getReviews(){return reviews; }
    public void setReviews(Map<String, Integer> reviews) {
        this.reviews = reviews;
    }
    public Map<String,String> getAppointments(){return appointments;}
    public void setAppointments(Map<String,String> appointments){this.appointments=appointments;}

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
       /* for (Map.Entry<String, Role> entry : services.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }*/
       result.put("name", name);
       result.put("phone", phone);
       result.put("location", location);
       result.put("payments", payments);
       result.put("insurances", insurances);
       result.put("services", services);
       result.put("workingHours", workingHours);
       result.put("numRated",numRated);
       result.put("sumRated",sumRated);
       result.put("reviews",reviews);
       result.put("appointments", appointments);
       return result;
    }

    public boolean isWithinWorkingHours(String day, String time){
        WorkingHours times = workingHours.get(day);
        if(times.getClosed())
            return false;
        return times.inRange(time);
    }

    public List<String> hasService(String keyword) {
        ArrayList<String> matchingNames = new ArrayList<String>();
        for(Map.Entry<String, Role> entry : services.entrySet()){
            String name = entry.getKey();
            if(name.contains(keyword)){
                matchingNames.add(name);
            }
        }
        return matchingNames;
    }


    public Map<String,String> updateAppointments(String apptDate, String day, String uid){
        //so we get the date: dd/mm/yyyy and we want to return the wait time
        // in the form 00:00
        // but we also want to store the date with the time that they would do their appointment next
        //and each would be 15 + min interval but have to make sure appt is on day & time correctly like
        // within the time its open / closed
        Map<String,String> results = new HashMap<String,String>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        try{
            LocalTime nowTime = LocalTime.now();
            System.err.println("now");
            LocalDate nowDate = LocalDate.now();
            System.err.println("datenow");
            LocalDate theAppt = LocalDate.parse(apptDate,formatter);
            System.err.println("appt");
            System.err.println(nowTime.toString());
            System.err.println(nowDate.toString());
            System.err.println(theAppt.toString());

            int NumApptOnDaySelected = 0;
            //remove all apointments that already happened
            LocalTime lastApptToday= null;

            if(appointments.size()>0) {
                for (Map.Entry<String, String> entry : appointments.entrySet()) {
                    String anAppt = entry.getValue();
                    if(!anAppt.equals("None")) {
                        String[] anApptParts = anAppt.split("#");
                        LocalDate anApptDate = LocalDate.parse(anApptParts[0],formatter);
                        LocalTime anApptTime = LocalTime.parse(anApptParts[1]);

                        if (nowDate.isAfter(anApptDate)) {
                            appointments.remove(anAppt);
                        } else if (theAppt.isEqual(anApptDate)) {
                            if(nowTime.isAfter(anApptTime)){
                                appointments.remove(entry.getKey());
                            }
                            else if(lastApptToday==null){
                                lastApptToday= anApptTime;
                                NumApptOnDaySelected += 1;
                            }
                            else if(lastApptToday.isBefore(anApptTime)){
                                lastApptToday = anApptTime;
                                NumApptOnDaySelected += 1;
                            }

                        }
                    }
                }
            }



            //Technically we should put some check to make sure were not making appts
            // after close time, but how tf you gonna make like 60 appts u feel
            LocalTime apptTime;
            if(nowDate.isEqual(theAppt)) {
                if(lastApptToday==null) {
                    apptTime = nowTime.plusMinutes(15 * NumApptOnDaySelected);
                    results.put("waitTime", LocalTime.parse("00:00").plusMinutes(15 * NumApptOnDaySelected).toString());
                }
                else{
                    apptTime = lastApptToday.plusMinutes(15);
                    results.put("waitTime", Long.toString(Duration.between(nowTime, apptTime).toMinutes()) );
                    //results.put("waitTime", LocalTime.parse("00:00").plusMinutes(15 * NumApptOnDaySelected).toString());
                }
                //results.put("waitTime", Duration.between(nowTime, apptTime).toString());
            }
            else {
                apptTime = LocalTime.parse(workingHours.get(day).getStartTime()).plusMinutes( 15* NumApptOnDaySelected);
                results.put("waitTime", LocalTime.parse("00:00").plusMinutes(15 * NumApptOnDaySelected).toString());
            }

            String appt = apptDate+ "#" + apptTime.toString();
            System.err.println("apptTime");
            results.put("appt",appt);
            appointments.put(uid, appt);
            System.err.println(appt);
            //return Duration.between(opening + appttime)
        }
        catch (Exception e){
            System.err.println("Error+ "+e.getMessage());
        }
        System.err.println("returning");
        return results;
    }
}
