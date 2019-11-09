package com.example.model;

import android.widget.Toast;

import com.example.finalproject.activity_sign_in;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class WalkInClinic {
    private String name;
    private Map<String,Role> services;

    public WalkInClinic(){}

    public WalkInClinic(String name){
        DatabaseReference dbclinic = FirebaseDatabase.getInstance().getReference().child("clinics");
        dbclinic.setValue(name);
        this.services = new HashMap<String, Role>();
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Role> entry : services.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public void addService(String nameOfService, Role role){
        if(!nameOfService.equals("") && nameOfService!=null) {
            services.put(nameOfService, role);
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("clinics").child(name);
            db.updateChildren(this.toMap());
        }
        else{
            System.out.println("No");
            //Toast.makeText(AdminAddServices.this, "Invalid service to add", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteService(String nameOfService){

        if(!nameOfService.equals("") && nameOfService!=null && services.containsKey(nameOfService)) {
            services.remove(nameOfService);
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("clinics").child(name);
            db.updateChildren(this.toMap());
        }
        else {
            System.out.println("No");
            //Toast.makeText(AdminAddServices.this, "Invalid service to delete", Toast.LENGTH_SHORT).show();
        }
    }

    public void editService(String nameOfService, Role role){
        if(!nameOfService.equals("") && nameOfService!=null && services.containsKey(nameOfService)) {
            services.put(nameOfService, role);
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("clinics").child(name);
            db.updateChildren(this.toMap());
        }
        else {
            System.out.println("No");
            //Toast.makeText(AdminAddServices.this, "Invalid service to edit", Toast.LENGTH_SHORT).show();
        }
    }
}
