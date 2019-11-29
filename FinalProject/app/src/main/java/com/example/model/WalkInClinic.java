package com.example.model;

import android.widget.Toast;

import com.example.finalproject.activity_sign_in;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
}
