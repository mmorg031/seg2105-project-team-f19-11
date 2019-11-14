package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WorkingHours extends AppCompatActivity {
    private ImageButton backButton; //goes back to menu page

    private TextView mondayHours; //shows the hours it is OPEN (see EditWorkingHours.java)
    private TextView tuesdayHours; //if clinic is CLOSED, it says CLOSED
    private TextView wednesdayHours; //input type = time
    private TextView thursdayHours;
    private TextView fridayHours;
    private TextView saturdayHours;
    private TextView sundayHours;

    private Button editHours; //goes to new page to edit the hours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);
    }
}
