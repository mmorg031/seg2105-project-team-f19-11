package com.example.model;

import java.util.Map;


public class Employee extends Person {
    Role role;
    public Employee(String name, String pass, String email, String chosenRole, Role role){
        super(name, pass, email, chosenRole);
        this.role=role;
    }
}

