package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SignUpChoice extends AppCompatActivity {
    private Button buttonClient;
    private Button buttonEmployee;
    private ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonClient = (Button) findViewById(R.id.clientBtn);
        buttonClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_sign_up();
            }
        });

        buttonEmployee = (Button) findViewById(R.id.empBtn);
        buttonEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_sign_up();
            }
        });

        buttonBack = (ImageButton) findViewById(R.id.backBtn);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_sign_in();
            }
        });
    }

    public void openactivity_sign_up(){
        Intent intent = new Intent(this, activity_sign_up.class);
        startActivity(intent);
    }

    public void openactivity_sign_in(){
        Intent intent = new Intent(this, activity_sign_in.class);
        startActivity(intent);
    }
}
