package com.example.model;

import android.widget.Toast;

import com.example.finalproject.activity_sign_in;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class WalkInClinic {
    private String name;
    private String location;
    private String phone;
    private Map<String,Boolean> payments;
    private Map<String,Boolean> insurances;
    private Map<String,Role> services;
    private Map<String,String> workingHours;

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

        this.workingHours = new HashMap<String,String>();
        workingHours.put("Sunday", "Closed");
        workingHours.put("Monday", "Closed");
        workingHours.put("Tuesday", "Closed");
        workingHours.put("Wednesday", "Closed");
        workingHours.put("Thursday", "Closed");
        workingHours.put("Friday", "Closed");
        workingHours.put("Saturday", "Closed");

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
    public Map<String,String> getWorkingHours(){return workingHours;}
    public void setWorkingHours(Map<String,String> workingHours){this.workingHours=workingHours;}

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
       return result;
    }



}
