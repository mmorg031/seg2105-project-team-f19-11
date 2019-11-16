package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.finalproject.R;
import com.example.finalproject.WelcomePage;

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
                openactivity_aboutPage();
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
                openactivity_hoursPage();
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

    public void openactivity_aboutPage(){
        Intent intent = new Intent(this, AboutClinic.class);
        startActivity(intent);
    }

    public void openactivity_paymentsPage(){
        Intent intent = new Intent(this, PaymentsPage.class);
        startActivity(intent);
    }
    public void openactivity_servicesPage(){
        Intent intent = new Intent(this, ServicesOfferedPage.class);
        startActivity(intent);
    }

    public void openactivity_hoursPage(){
        Intent intent = new Intent(this, WorkingHours.class);
        startActivity(intent);
    }

    public void openactivity_welcomePage(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
}
