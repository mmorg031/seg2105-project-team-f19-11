package com.example.model;

import android.widget.Toast;

import com.example.finalproject.activity_sign_in;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;


public class WalkInClinic {
    private String name;
    private Map<String,Role> services;

    public WalkInClinic(String name){
        this.name = name;
    }

    public void addService(String nameOfService, Role role){
        if(!nameOfService.equals("") && nameOfService!=null) {
            services.put(nameOfService, role);
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("clinics").child(name);
            db.setValue(this);
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
            db.setValue(this);
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
            db.setValue(this);
        }
        else {
            System.out.println("No");
            //Toast.makeText(AdminAddServices.this, "Invalid service to edit", Toast.LENGTH_SHORT).show();
        }
    }
}
