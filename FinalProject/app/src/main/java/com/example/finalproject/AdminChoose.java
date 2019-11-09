package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AdminChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choose);

        //DELETE ME LATER
        startActivity(new Intent(this, edit_service.class));
    }

    public void openPatientList(){
        Intent intent = new Intent(this, PatientList.class);
        startActivity(intent);
    }
}
