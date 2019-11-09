package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminChoose extends AppCompatActivity {
    private Button serviceBtn;
    private Button userBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choose);

        serviceBtn = (Button) findViewById(R.id.servicesBtn);
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceList();
            }
        });

        userBtn = (Button) findViewById(R.id.usersBtn);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPatientList();
            }
        });
    }

    public void openPatientList(){
        Intent intent = new Intent(this, PatientList.class);
        startActivity(intent);
    }

    public void openServiceList(){
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }
}
