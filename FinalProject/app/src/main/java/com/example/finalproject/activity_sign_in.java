package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_sign_in extends AppCompatActivity {
    private EditText ETemail, ETpassword;
    private Button buttonCreateAccount;
    private Button buttonLogin;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();
        ETemail = (EditText) findViewById(R.id.enter_email);
        ETpassword = (EditText) findViewById(R.id.enter_password);

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

<<<<<<< HEAD
=======

       /* mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(activity_sign_in.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_sign_in.this, WelcomePage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity_sign_in.this, "Please Log in", Toast.LENGTH_SHORT).show();

                }
            }
        };*/
>>>>>>> 85b6a0355dbd00bbaa033da58aeef8a8a676f581
    }

    public void openWelcomePage() {

        String email = ETemail.getText().toString();
        String password = ETpassword.getText().toString();

        if (email.isEmpty() && password.isEmpty()){
            Toast.makeText(activity_sign_in.this, "Both fields are blank", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {
            ETemail.setError("Please Provide an Email");
            ETemail.requestFocus();
        } else if (password.isEmpty()) {
            ETpassword.setError("Please Provide a Password");
            ETpassword.requestFocus();
        }
        else if( email.equals("admin") && password.equals("5T5ptQ")){
            Intent intent = new Intent(activity_sign_in.this, AdminAddServices.class);
            startActivity(intent);
        }
        else {
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity_sign_in.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(activity_sign_in.this, "Incorrect Login", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(activity_sign_in.this, "You are logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_sign_in.this, WelcomePage.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }


    public void openSignUpChoice(){
        Intent intent = new Intent(this, SignUpChoice.class);
        startActivity(intent);
    }

<<<<<<< HEAD
=======
   // @Override
    /*protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }*/
>>>>>>> 85b6a0355dbd00bbaa033da58aeef8a8a676f581
}

