package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.dev3.RateClinic;

public class ConfirmationPage extends AppCompatActivity {

    private Button goToRateClinicButton;
    private TextView dayOfAppointment; //shows day of the booked appointment
    private TextView timeOfAppointment; //shows time of the booked appointment
    private TextView waitingTime; //shows waiting time of the booked appointment
    //best way to implement wait time is to just see how many people have an appointment booked
    //BEFORE this user on that day, then just multiply it by 15min and show result
    //(assuming each client has a 15min session based on the description on rubric)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        goToRateClinicButton = findViewById(R.id.rateTheClinicBtn);
        goToRateClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_rateTheClinic();
            }
        });

    }


    public void openactivity_rateTheClinic(){
        Intent intent = new Intent(this, RateClinic.class);
        startActivity(intent);
    }
}
