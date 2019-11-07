package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PatientList extends AppCompatActivity {

    private ImageButton buttonBack ;
    private Button employeeButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        employeeButton = (Button) findViewById(R.id.employeeButton);
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_EmployeeList();
            }
        });
    }
    public void openactivity_EmployeeList(){
        Intent intent = new Intent(this, EmployeeList.class);
        startActivity(intent);
    }
}
