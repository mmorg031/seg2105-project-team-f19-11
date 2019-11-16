package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalproject.R;

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

        backButton = (ImageButton) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_clinicAbout();
            }
        });
    }

    public void openactivity_clinicAbout(){
        Intent intent = new Intent(this, ClinicAbout.class);
        startActivity(intent);
    }

}
