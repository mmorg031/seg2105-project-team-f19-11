package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_sign_up extends AppCompatActivity {
    private EditText ETname;
    private EditText ETemail;
    private EditText ETpassword;
    private Button buttonCreateAccount;
    private Button buttonLogin;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        ETname = (EditText) findViewById(R.id.enter_name);
        ETemail = (EditText) findViewById(R.id.enter_email);
        ETpassword = (EditText) findViewById(R.id.enter_password);


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
        String email = ETemail.getText().toString();
        String password = ETpassword.getText().toString();
        String name = ETname.getText().toString();

        if(email.isEmpty()){
            ETemail.setError("Please Provide an Email");
            ETemail.requestFocus();
        }
        else if(password.isEmpty()){
            ETpassword.setError("Please Provide a Password");
            ETpassword.requestFocus();
        }
        else if(name.isEmpty()){
            ETname.setError("Please provide a Name");
            ETname.requestFocus();
        }
        else{
            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity_sign_up.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(activity_sign_up.this, "Sign up Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(activity_sign_up.this, WelcomePage.class);
                        startActivity(intent);
                    }
                }
            });

        }

    }



    public void openactivity_sign_in(){
        Intent intent = new Intent(this, activity_sign_in.class);
        startActivity(intent);
    }
}
