package com.example.model;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//or user class same thing
public class Person {
    private String name;
    private String password;
    private String email;
    private Role role; //enumerate later on


    public Person(){}

    public Person(String name, String pass, String email, Role role){
        // Static getInstance method is called with hashing SHA
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.password = new String (digest.digest(pass.getBytes(StandardCharsets.UTF_8)));
        }
        catch (NoSuchAlgorithmException e){
            System.out.println("SHA-256 is not an algorithm lol??: "+e.getMessage());
        }
        this.name=name;
        this.email=email;

        if(role.toString().equals("Employee"))
            this.role=Role.Employee;
        else if (role.toString().equals("Patient"))
            this.role=Role.Patient;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email){this.email=email;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            this.password = new String(digest.digest(password.getBytes(StandardCharsets.UTF_8)));
        }
        catch (NoSuchAlgorithmException e){
            System.out.println("Whatcha doin");
        }
    }

    public String getName(){

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Role getRole(){
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("password", password);
        result.put("email", email);
        result.put("role", role);

        return result;
    }

}
