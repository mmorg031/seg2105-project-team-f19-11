package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class
EmployeeList extends AppCompatActivity {

    private ImageButton buttonBack ;
    private Button patientButton ;
    private Button popUp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        patientButton = (Button) findViewById(R.id.patientButton);
        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_PatientList();
            }
        });
        popUp = (Button) findViewById(R.id.pop) ;
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmployeeList.this, Pop.class));
            }
        });
    }
    public void openactivity_PatientList(){
        Intent intent = new Intent(this, PatientList.class);
        startActivity(intent);
    }
}