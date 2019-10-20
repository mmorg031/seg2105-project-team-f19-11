package com.example.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

//or user class same thing
public class Person {
    private String name;
    private byte[] password;
    private String email;
    private String role; //enumerate later on

    public Person(String name, String pass, String email){
        // Static getInstance method is called with hashing SHA
        //MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //password = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        this.name=name;
        this.email=email;

    }
}
