package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.WalkInClinic;
import com.example.model.WorkingHours;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowWorkingHours extends AppCompatActivity {
    private ImageButton backButton; //goes back to menu page

    private TextView mondayHours; //shows the hours it is OPEN (see EditWorkingHours.java)
    private TextView tuesdayHours; //if clinic is CLOSED, it says CLOSED
    private TextView wednesdayHours; //input type = time
    private TextView thursdayHours;
    private TextView fridayHours;
    private TextView saturdayHours;
    private TextView sundayHours;
    private FirebaseAuth mFirebaseAuth;
    private ImageButton editHours; //goes to new page to edit the hours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);

        backButton = (ImageButton) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_clinicAbout();
            }
        });

        editHours = (ImageButton) findViewById(R.id.editHoursBtn);
        editHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_editWorkingHours();
            }
        });

        sundayHours = (TextView)  findViewById(R.id.sundayHours);
        mondayHours = (TextView)  findViewById(R.id.mondayHours);
        tuesdayHours = (TextView)  findViewById(R.id.tuesdayHours);
        wednesdayHours = (TextView)  findViewById(R.id.wednesdayHours);
        thursdayHours = (TextView)  findViewById(R.id.thursdayHours);
        fridayHours = (TextView)  findViewById(R.id.fridayHours);
        saturdayHours = (TextView)  findViewById(R.id.saturdayHours);


        String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Employee user = dataSnapshot.getValue(Employee.class);
                    Map<String, WorkingHours> workingHours = user.getClinic().getWorkingHours();

                    if(workingHours.get("Sunday").getClosed())
                        sundayHours.setText("Sunday: Closed");
                    else
                        sundayHours.setText("Sunday: "+workingHours.get("Sunday").getStartTime()+"-"+workingHours.get("Sunday").getEndTime());

                    if(workingHours.get("Monday").getClosed())
                        mondayHours.setText("Monday: Closed");
                    else
                        mondayHours.setText("Monday: "+workingHours.get("Monday").getStartTime()+"-"+workingHours.get("Monday").getEndTime());

                    if(workingHours.get("Tuesday").getClosed())
                        tuesdayHours.setText("Tuesday: Closed");
                    else
                        tuesdayHours.setText("Tuesday: "+workingHours.get("Tuesday").getStartTime()+"-"+workingHours.get("Tuesday").getEndTime());

                    if(workingHours.get("Wednesday").getClosed())
                        wednesdayHours.setText("Wednesday: Closed");
                    else
                        wednesdayHours.setText("Wednesday: "+workingHours.get("Wednesday").getStartTime()+"-"+workingHours.get("Wednesday").getEndTime());

                    if(workingHours.get("Thursday").getClosed())
                        thursdayHours.setText("Thursday: Closed");
                    else
                        thursdayHours.setText("Thursday: "+workingHours.get("Thursday").getStartTime()+"-"+workingHours.get("Thursday").getEndTime());

                    if(workingHours.get("Friday").getClosed())
                        fridayHours.setText("Friday: Closed");
                    else
                        fridayHours.setText("Friday: "+workingHours.get("Friday").getStartTime()+"-"+workingHours.get("Friday").getEndTime());

                    if(workingHours.get("Saturday").getClosed())
                        saturdayHours.setText("Saturday: Closed");
                    else
                        saturdayHours.setText("Saturday: "+workingHours.get("Saturday").getStartTime()+"-"+workingHours.get("Saturday").getEndTime());


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

    public void openactivity_editWorkingHours(){
        Intent intent = new Intent(this, EditWorkingHours.class);
        startActivity(intent);
    }

}
