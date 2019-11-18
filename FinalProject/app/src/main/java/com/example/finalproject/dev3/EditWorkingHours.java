package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.WorkingHours;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class EditWorkingHours extends AppCompatActivity {
    private ImageButton backButton; //goes back to about page
    private ImageButton saveButton; //saves new info

    private Switch openMonday; //if switch is on, that means clinic is OPEN that day
    private Switch openTuesday; //if it is on, then they CAN choose the hours it is open
    private Switch openWednesday; //if switch is off, that means clinic is CLOSED that day
    private Switch openThursday; //if it is off, then they CANNOT choose the hours
    private Switch openFriday;
    private Switch openSaturday;
    private Switch openSunday;

    private EditText startTimeMonday; //input type = time
    private EditText endTimeMonday;
    private EditText startTimeTuesday; //edit START time for the specific day
    private EditText endTimeTuesday; //edit END time for the specific day
    private EditText startTimeWednesday;
    private EditText endTimeWednesday;
    private EditText startTimeThursday;
    private EditText endTimeThursday;
    private EditText startTimeFriday;
    private EditText endTimeFriday;
    private EditText startTimeSaturday;
    private EditText endTimeSaturday;
    private EditText startTimeSunday;
    private EditText endTimeSunday;

    private FirebaseAuth mFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_working_hours);

        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goback();
            }
        });

        saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_saveWorkingHourChanges();
            }
        });

        openMonday = findViewById(R.id.switchMonday);
        openMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Monday", isChecked);
            }
        });
        openTuesday = findViewById(R.id.switchTuesday);
        openTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Tuesday", isChecked);
            }
        });
        openWednesday = findViewById(R.id.switchWednesday);
        openWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Wednesday", isChecked);
            }
        });
        openThursday = findViewById(R.id.switchThursday);
        openThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Thursday", isChecked);
            }
        });
        openFriday = findViewById(R.id.switchFriday);
        openFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Friday", isChecked);
            }
        });
        openSaturday  = findViewById(R.id.switchSaturday);
        openSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Saturday", isChecked);
            }
        });
        openSunday  = findViewById(R.id.switchSunday);
        openSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleEditWorkingHours("Sunday", isChecked);
            }
        });


        startTimeMonday = findViewById(R.id.startTimeMon);
        endTimeMonday = findViewById(R.id.endTimeMon);
        startTimeTuesday = findViewById(R.id.startTimeTues);
        endTimeTuesday = findViewById(R.id.endTimeTues);
        startTimeWednesday = findViewById(R.id.startTimeWed);
        endTimeWednesday = findViewById(R.id.endTimeWed);
        startTimeThursday  = findViewById(R.id.startTimeThurs);
        endTimeThursday  = findViewById(R.id.endTimeThurs);
        startTimeFriday = findViewById(R.id.startTimeFri);
        endTimeFriday = findViewById(R.id.endTimeFri);
        startTimeSaturday = findViewById(R.id.startTimeSat);
        endTimeSaturday = findViewById(R.id.endTimeSat);
        startTimeSunday = findViewById(R.id.startTimeSun);
        endTimeSunday = findViewById(R.id.endTimeSun);


        initializeState();
    }

    public void initializeState(){
        String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Employee user = dataSnapshot.getValue(Employee.class);
                    Map<String, WorkingHours> workingHours = user.getClinic().getWorkingHours();

                    if(workingHours.get("Sunday").getClosed())
                        toggleEditWorkingHours("Sunday",false);
                    else {
                        toggleEditWorkingHours("Sunday",true);
                        sundayHours.setText("Sunday: " + workingHours.get("Sunday").getStartTime() + "-" + workingHours.get("Sunday").getEndTime());
                    }
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

    public void toggleEditWorkingHours(String day, boolean open){
        if(day.equals("Monday")){
            startTimeMonday.setEnabled(open);
            endTimeMonday.setEnabled(open);
        }
        else if(day.equals("Tuesday")){
            startTimeTuesday.setEnabled(open);
            endTimeTuesday.setEnabled(open);
        }
        else if(day.equals("Wednesday")){
            startTimeWednesday.setEnabled(open);
            endTimeWednesday.setEnabled(open);
        }
        else if(day.equals("Thursday")){
            startTimeThursday.setEnabled(open);
            endTimeThursday.setEnabled(open);
        }
        else if(day.equals("Friday")){
            startTimeFriday.setEnabled(open);
            endTimeFriday.setEnabled(open);
        }
        else if(day.equals("Saturday")){
            startTimeSaturday.setEnabled(open);
            endTimeSaturday.setEnabled(open);
        }
        else if(day.equals("Sunday")){
            startTimeSunday.setEnabled(open);
            endTimeSunday.setEnabled(open);
        }
    }

    public void openactivity_goback(){
        Intent intent = new Intent(this, ShowWorkingHours.class);
        startActivity(intent);
    }

    public void openactivity_saveWorkingHourChanges(){
        //save changes and go back
        openactivity_goback();
    }
}
