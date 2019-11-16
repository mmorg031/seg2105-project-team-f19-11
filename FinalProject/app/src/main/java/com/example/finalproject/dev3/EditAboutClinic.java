package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalproject.R;

public class EditAboutClinic extends AppCompatActivity {
    private ImageButton backButton; //goes back to about page
    private ImageButton saveButton; //saves new info

    private EditText nameOfClinic; //type new name
    private EditText addressOfClinic; //type new address
    private EditText phoneOfClinic; //type new phone number, input type = phone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_clinic);

        Bundle extras = getIntent().getExtras();

        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_aboutClinic();
            }
        });

        saveButton = findViewById(R.id.saveBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClinicInfo();
            }
        });

        nameOfClinic =  findViewById(R.id.writeName);
        addressOfClinic = findViewById(R.id.writeAddress);
        phoneOfClinic = findViewById(R.id.writePhone);
    }


    public void openactivity_aboutClinic(){
        Intent intent = new Intent(this, AboutClinic.class);
        startActivity(intent);
    }

    public void saveClinicInfo(){
        //save and open about
        openactivity_aboutClinic();
    }
}
