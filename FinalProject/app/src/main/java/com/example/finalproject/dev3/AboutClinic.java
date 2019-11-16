package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalproject.R;

public class AboutClinic extends AppCompatActivity {
    private ImageButton backButton; //goes back to menu page
    private TextView nameOfClinic; //shows the saved name of clinic
    private TextView addressOfClinic; //shows the saved address associated with clinic
    private TextView phoneOfClinic; //shows the saved phone number associated with clinic
    private Button editAbout; //goes to new page to edit information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_clinic);

        backButton = (ImageButton) findViewById(R.id.backBtnPat) ;
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
