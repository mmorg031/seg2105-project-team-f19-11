package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_sign_up extends AppCompatActivity {
    private Button buttonCreateAccount;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonCreateAccount = (Button) findViewById(R.id.create_account);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomePage();
            }
        });

        buttonLogin = (Button) findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_sign_in();
            }
        });
    }

    public void openWelcomePage(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
    public void openactivity_sign_in(){
        Intent intent = new Intent(this, activity_sign_in.class);
        startActivity(intent);
    }
}
