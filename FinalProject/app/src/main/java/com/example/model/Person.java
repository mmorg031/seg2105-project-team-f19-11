package com.example.model;

<<<<<<< HEAD

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
import java.util.Map;

 enum Role{
    Doctor,
    Nurse,
    Staff,
    Patient,
    Employee,
     Administrator
}
=======
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
>>>>>>> 85b6a0355dbd00bbaa033da58aeef8a8a676f581

//or user class same thing
public class Person {
    private String name;
<<<<<<< HEAD
    private String password;
    private String email;
    private Role role; //enumerate later on


    public Person(){}

    public Person(String name, String pass, String email, String chosenRole){
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

        if(chosenRole.equals("Employee"))
            this.role=Role.Employee;
        else if (chosenRole.equals("Patient"))
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
=======
    private byte[] password;
    private String email;
    private String role; //enumerate later on

    public Person(String name, String pass, String email){
        // Static getInstance method is called with hashing SHA
        //MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //password = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        this.name=name;
        this.email=email;
>>>>>>> 85b6a0355dbd00bbaa033da58aeef8a8a676f581

    public void setName(String name) {
        this.name = name;
    }


    public String getRole(){
        return role.toString();
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

    public static void displayAllUsers(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        collectNames ((Map<String, Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});
    }

    public static void collectNames(Map<String, Object > users){
        ArrayList<Person> people = new ArrayList<>();

        for (Map.Entry<String, Object> entry : users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            people.add((Person) singleUser );
        }

    }

}
