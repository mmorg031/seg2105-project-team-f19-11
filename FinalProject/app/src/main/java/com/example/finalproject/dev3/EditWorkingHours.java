package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.activity_sign_in;
import com.example.model.Employee;
import com.example.model.WorkingHours;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.example.model.WorkingHours.isCorrectTimeInput;

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
        startTimeSaturday = findViewById(R.id.startTimeSaturday);
        endTimeSaturday = findViewById(R.id.endTimeSaturday);
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
                        startTimeSunday.setText(workingHours.get("Sunday").getStartTime());
                        endTimeSunday.setText(workingHours.get("Sunday").getEndTime());
                    }
                    if(workingHours.get("Monday").getClosed())
                        toggleEditWorkingHours("Monday",false);
                    else {
                        toggleEditWorkingHours("Monday", true);
                        startTimeMonday.setText(workingHours.get("Monday").getStartTime());
                        endTimeMonday.setText(workingHours.get("Monday").getEndTime());
                    }

                    if(workingHours.get("Tuesday").getClosed())
                        toggleEditWorkingHours("Tuesday",false);
                    else {
                        toggleEditWorkingHours("Tuesday",true);
                        startTimeTuesday.setText(workingHours.get("Tuesday").getStartTime());
                        endTimeTuesday.setText(workingHours.get("Tuesday").getEndTime());
                    }
                    if(workingHours.get("Wednesday").getClosed())
                        toggleEditWorkingHours("Wednesday",false);
                    else {
                        toggleEditWorkingHours("Wednesday", true);
                        startTimeWednesday.setText(workingHours.get("Wednesday").getStartTime());
                        endTimeWednesday.setText(workingHours.get("Wednesday").getEndTime());
                    }
                    if(workingHours.get("Thursday").getClosed())
                        toggleEditWorkingHours("Thursday",false);
                    else {
                        toggleEditWorkingHours("Thursday", true);
                        startTimeThursday.setText(workingHours.get("Thursday").getStartTime());
                        endTimeThursday.setText(workingHours.get("Thursday").getEndTime());
                    }
                    if(workingHours.get("Friday").getClosed())
                        toggleEditWorkingHours("Friday",false);
                    else {
                        toggleEditWorkingHours("Friday",true);
                        startTimeFriday.setText(workingHours.get("Friday").getStartTime());
                        endTimeFriday.setText(workingHours.get("Friday").getEndTime());
                    }
                    if(workingHours.get("Saturday").getClosed())
                        toggleEditWorkingHours("Saturday",false);
                    else{
                        toggleEditWorkingHours("Saturday",true);
                        startTimeSunday.setText(workingHours.get("Saturday").getStartTime());
                        endTimeSunday.setText(workingHours.get("Saturday").getEndTime());
                    }


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

        String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/"+userID);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Employee user = dataSnapshot.getValue(Employee.class);
                    Map<String, WorkingHours> workingHours = user.getClinic().getWorkingHours();

                    if(openSunday.isChecked()) {
                        String sundayStart = startTimeSunday.getText().toString();
                        String sundayEnd = endTimeSunday.getText().toString();
                        if (!sundayStart.isEmpty()&& !sundayEnd.isEmpty() && isCorrectTimeInput(sundayStart,sundayEnd)) {
                            workingHours.put("Sunday", new WorkingHours(sundayStart, sundayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Sunday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Sunday",new WorkingHours("08:00","16:00",true));
                    }



                    if(openMonday.isChecked()) {
                        String mondayStart = startTimeMonday.getText().toString();
                        String mondayEnd = endTimeMonday.getText().toString();
                        if (!mondayStart.isEmpty()&& !mondayEnd.isEmpty() && isCorrectTimeInput(mondayStart,mondayEnd)) {
                            workingHours.put("Monday", new WorkingHours(mondayStart, mondayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Monday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Monday",new WorkingHours("08:00","16:00",true));
                    }

                    if(openTuesday.isChecked()) {
                        String tuesdayStart = startTimeTuesday.getText().toString();
                        String tuesdayEnd = endTimeTuesday.getText().toString();
                        if (!tuesdayStart.isEmpty()&& !tuesdayEnd.isEmpty() && isCorrectTimeInput(tuesdayStart,tuesdayEnd)) {
                            workingHours.put("Tuesday", new WorkingHours(tuesdayStart, tuesdayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Tuesday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Tuesday",new WorkingHours("08:00","16:00",true));
                    }

                    if(openWednesday.isChecked()) {
                        String wednesdayStart = startTimeWednesday.getText().toString();
                        String wednesdayEnd = endTimeWednesday.getText().toString();
                        if (!wednesdayStart.isEmpty()&& !wednesdayEnd.isEmpty() && isCorrectTimeInput(wednesdayStart,wednesdayEnd)) {
                            workingHours.put("Wednesday", new WorkingHours(wednesdayStart, wednesdayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Wednesday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Wednesday",new WorkingHours("08:00","16:00",true));
                    }

                    if(openThursday.isChecked()) {
                        String thursdayStart = startTimeThursday.getText().toString();
                        String thursdayEnd = endTimeThursday.getText().toString();
                        if (!thursdayStart.isEmpty()&& !thursdayEnd.isEmpty() && isCorrectTimeInput(thursdayStart,thursdayEnd)) {
                            workingHours.put("Thursday", new WorkingHours(thursdayStart, thursdayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Thursday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Thursday",new WorkingHours("08:00","16:00",true));
                    }

                    if(openFriday.isChecked()) {
                        String fridayStart = startTimeFriday.getText().toString();
                        String fridayEnd = endTimeFriday.getText().toString();
                        if (!fridayStart.isEmpty()&& !fridayEnd.isEmpty() && isCorrectTimeInput(fridayStart,fridayEnd)) {
                            workingHours.put("Friday", new WorkingHours(fridayStart, fridayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Friday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Friday",new WorkingHours("08:00","16:00",true));
                    }

                    if(openSaturday.isChecked()) {
                        String saturdayStart = startTimeSaturday.getText().toString();
                        String saturdayEnd = endTimeSaturday.getText().toString();
                        if (!saturdayStart.isEmpty()&& !saturdayEnd.isEmpty() && isCorrectTimeInput(saturdayStart,saturdayEnd)) {
                            workingHours.put("Saturday", new WorkingHours(saturdayStart, saturdayEnd, false));
                        } else{
                            Toast.makeText(EditWorkingHours.this, "Input Valid Time for Saturday in format 00:00", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        workingHours.put("Saturday",new WorkingHours("08:00","16:00",true));
                    }

                    user.getClinic().setWorkingHours(workingHours);
                    db.setValue(user);
                    openactivity_goback();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });

    }
}
