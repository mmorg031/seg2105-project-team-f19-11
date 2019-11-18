package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.finalproject.R;

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

    }

    public void openactivity_goback(){
        Intent intent = new Intent(this, WorkingHours.class);
        startActivity(intent);
    }

    public void openactivity_saveWorkingHourChanges(){
        //save changes and go back
        openactivity_goback();
    }
}
