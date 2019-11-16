package com.example.model;

import java.util.HashMap;
import java.util.Map;


public class Employee extends Person {
    WalkInClinic clinic;

    public Employee(){
    }

    public Employee(String name, String pass, String email, Role role){
        super(name, pass, email, role);
    }

    public Employee(String name, String pass, String email, Role role, WalkInClinic clinic){
        super(name, pass, email, role);
        this.clinic = clinic;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.getName());
        result.put("password", this.getPassword());
        result.put("email", this.getEmail());
        result.put("role", this.getRole());
        result.put("clinic", this.getClinic());

        return result;
    }

    public void setClinic(WalkInClinic clinic){
        this.clinic=clinic;
    }

    public WalkInClinic getClinic(){
        return clinic;
    }
}

