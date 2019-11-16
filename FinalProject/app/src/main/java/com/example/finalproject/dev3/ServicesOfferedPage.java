package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.finalproject.R;

public class ServicesOfferedPage extends AppCompatActivity {

    private Button edit ;
    private ImageButton back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_offered_page);

        edit = (Button) findViewById(R.id.editServicesBtn) ;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_editServicesPage();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_aboutPage();
            }
        });
    }

    public void openactivity_editServicesPage(){
        Intent intent = new Intent(this, EditServicesPage.class);
        startActivity(intent);
    }
    public void openactivity_aboutPage(){
        Intent intent = new Intent(this, ClinicAbout.class);
        startActivity(intent);
    }
}
