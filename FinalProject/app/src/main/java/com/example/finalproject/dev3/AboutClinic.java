package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class AboutClinic extends AppCompatActivity {
    private ImageButton backButton; //goes back to menu page
    private TextView nameOfClinic; //shows the saved name of clinic
    private TextView addressOfClinic; //shows the saved address associated with clinic
    private TextView phoneOfClinic; //shows the saved phone number associated with clinic
    private ImageButton editAbout; //goes to new page to edit information
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_clinic);

        backButton = (ImageButton) findViewById(R.id.backBtnPat) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_clinicAbout();
            }
        });

        editAbout = (ImageButton) findViewById(R.id.editAboutBtn);
        editAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_editClinicAbout();
            }
        });

        nameOfClinic = findViewById(R.id.nameText);
        addressOfClinic = findViewById(R.id.addressText);
        phoneOfClinic = findViewById(R.id.phoneText);

        String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Employee user = dataSnapshot.getValue(Employee.class);
                    WalkInClinic clinic = user.getClinic();

                    nameOfClinic.setText(clinic.getName());
                    addressOfClinic.setText(clinic.getLocation());
                    phoneOfClinic.setText(clinic.getPhone());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });




    }

    public void openactivity_clinicAbout(){
        Intent intent = new Intent(this, ClinicAbout.class);
        startActivity(intent);
    }

    public void openactivity_editClinicAbout(){
        Intent intent = new Intent(this, EditAboutClinic.class);
        intent.putExtra("clinicName", nameOfClinic.getText());
        intent.putExtra("clinicAddress", addressOfClinic.getText());
        intent.putExtra("clinicPhone", phoneOfClinic.getText());
        startActivity(intent);
    }
}
