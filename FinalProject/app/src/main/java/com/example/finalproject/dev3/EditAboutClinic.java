package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAboutClinic extends AppCompatActivity {
    private ImageButton backButton; //goes back to about page
    private ImageButton saveButton; //saves new info

    private EditText nameOfClinic; //type new name
    private EditText addressOfClinic; //type new address
    private EditText phoneOfClinic; //type new phone number, input type = phone

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_clinic);

        Bundle extras = getIntent().getExtras();

        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_aboutClinic();
            }
        });

        saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClinicInfo();
            }
        });

        nameOfClinic =  findViewById(R.id.writeName);
        nameOfClinic.setText(extras.get("clinicName").toString());
        addressOfClinic = findViewById(R.id.writeAddress);
        addressOfClinic.setText(extras.get("clinicAddress").toString());
        phoneOfClinic = findViewById(R.id.writePhone);
        phoneOfClinic.setText(extras.get("clinicPhone").toString());


    }


    public void openactivity_aboutClinic(){
        Intent intent = new Intent(this, AboutClinic.class);
        startActivity(intent);
    }

    public void saveClinicInfo(){
        //save and open about
        final String name = nameOfClinic.getText().toString();
        final String address = addressOfClinic.getText().toString();
        final String phone = phoneOfClinic.getText().toString();

        if(name.isEmpty()){
            nameOfClinic.setError("Please Provide a Name");
            nameOfClinic.requestFocus();
        }
        else if(address.isEmpty()){
            addressOfClinic.setError("Please Provide an Address");
            addressOfClinic.requestFocus();
        }
        else if(phone.isEmpty()){
            phoneOfClinic.setError("Please provide a Phone Number");
            phoneOfClinic.requestFocus();
        }
        else {
            String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + userID);

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                        Employee user = dataSnapshot.getValue(Employee.class);
                        WalkInClinic clinic = new WalkInClinic(name, address, phone);
                        user.setClinic(clinic);
                        db.setValue(user);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }


            });
            openactivity_aboutClinic();
        }
    }
}
