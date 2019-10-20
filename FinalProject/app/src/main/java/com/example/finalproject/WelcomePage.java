package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WelcomePage extends AppCompatActivity {
    private TextView welcome;
    private TextView logInAs;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        welcome = (TextView) findViewById(R.id.welcomeText);
        logInAs = (TextView) findViewById(R.id.loggedInAsText);

        final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);
        //welcome.setText(userID);
/*
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User post = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        //DatabaseReference ref = database.child("users");
        /*final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid(); */

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for(DataSnapshot snapShot : dataSnapshot.getChildren()) {

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String role = dataSnapshot.child("role").getValue(String.class);

                    welcome.setText("Welcome, " + name);
                    logInAs.setText("You are logged in as: " + role);
                //}
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }



        });


//        }


    }
}
