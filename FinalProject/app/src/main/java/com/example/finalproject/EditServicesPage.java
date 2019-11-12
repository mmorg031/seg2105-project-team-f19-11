package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EditServicesPage extends AppCompatActivity {

    private Button save1;
    private ImageButton save2;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services_page);

        // DON'T KNOW HOW TO IMPLEMENT THE ACTUAL SAVE FUNCTIONALITY -S.M.

        save1 = (Button) findViewById(R.id.saveServicesBtn);
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_servicesOfferedPage2();
            }
        });

        save2 = (ImageButton) findViewById(R.id.saveBtn);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_servicesOfferedPage2();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_servicesOfferedPage1();
            }
        });
    }

    public void openactivity_servicesOfferedPage1(){
        Intent intent = new Intent(this, ServicesOfferedPage.class);
        startActivity(intent);
    }
    public void openactivity_servicesOfferedPage2(){
        Intent intent = new Intent(this, ServicesOfferedPage.class);
        startActivity(intent);
    }
}
