package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalproject.R;
import com.example.finalproject.WelcomePage;
import com.example.model.Employee;
import com.example.model.Person;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class completeClinicProfile extends AppCompatActivity {
    private EditText nameText;
    private EditText addressText;
    private EditText phoneText;
    private Button save;
    private static boolean doneProfile=false;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_clinic_profile);

        nameText = (EditText) findViewById(R.id.nameText);
        addressText = (EditText) findViewById(R.id.addressText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        save = (Button) findViewById(R.id.button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClinicInfo();
            }
        });

    }

    public void saveClinicInfo(){
        final String name = nameText.getText().toString();
        final String address = addressText.getText().toString();
        final String phone = phoneText.getText().toString();

        if(name.isEmpty()){
            nameText.setError("Please Provide a Name");
            nameText.requestFocus();
        }
        else if(address.isEmpty()){
            addressText.setError("Please Provide an Address");
            addressText.requestFocus();
        }
        else if(phone.isEmpty()){
            phoneText.setError("Please provide a Phone Number");
            phoneText.requestFocus();
        }
        else{
            final WalkInClinic clinic = new WalkInClinic(name, address, phone);
            final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                            if(!doneProfile) {
                                Employee user = dataSnapshot.getValue(Employee.class);
                                //db.removeValue();
                                user.setClinic(clinic);
                                db.setValue(user);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }


                });

                Intent intent = new Intent(this, WelcomePage.class);
                startActivity(intent);
            }
        
    }



}
