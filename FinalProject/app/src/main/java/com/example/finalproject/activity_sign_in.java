package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_sign_in extends AppCompatActivity {
    private Button buttonCreateAccount;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonCreateAccount = (Button) findViewById(R.id.create_account);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpChoice();
            }
        });

        buttonLogin = (Button) findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWelcomePage();
            }
        });
    }

    public void openWelcomePage(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }
    public void openSignUpChoice(){
        Intent intent = new Intent(this, SignUpChoice.class);
        startActivity(intent);
    }
}

