package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ClinicAbout extends AppCompatActivity {

    private Button about ;
    private Button payments ;
    private Button services ;
    private Button hours ;
    private ImageButton back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_about);

        about = (Button) findViewById(R.id.aboutBtn) ;
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OPEN THE ABOUT ACTIVITY!
            }
        });

        payments = (Button) findViewById(R.id.paymentsBtn) ;
        payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_paymentsPage();
            }
        });

        services = (Button) findViewById(R.id.servicesBtn) ;
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_servicesPage();
            }
        });

        hours = (Button) findViewById(R.id.hoursBtn) ;
        hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // OPEN THE HOURS ACTIVITY!
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_welcomePage();
            }
        });

    }

    public void openactivity_paymentsPage(){
        Intent intent = new Intent(this, PaymentsPage.class);
        startActivity(intent);
    }
    public void openactivity_servicesPage(){
        Intent intent = new Intent(this, ServicesOfferedPage.class);
        startActivity(intent);
    }
    public void openactivity_welcomePage(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
}
