package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

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

    //Will possibly need these too (PREVIOUS of above):
    private String previousOpenMonday;
    private String previousOpenTuesday;
    private String previousOpenWednesday;
    private String previousOpenThursday;
    private String previousOpenFriday;
    private String previousOpenSaturday;
    private String previousOpenSunday;

    private String previousStartTimeMonday;
    private String previousEndTimeMonday;
    private String previousStartTimeTuesday;
    private String previousEndTimeTuesday;
    private String previousStartTimeWednesday;
    private String previousEndTimeWednesday;
    private String previousStartTimeThursday;
    private String previousEndTimeThursday;
    private String previousStartTimeFriday;
    private String previousEndTimeFriday;
    private String previousStartTimeSaturday;
    private String previousEndTimeSaturday;
    private String previousStartTimeSunday;
    private String previousEndTimeSunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_working_hours);
    }
}
