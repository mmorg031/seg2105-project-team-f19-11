package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.model;
import com.example.model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class activity_sign_in extends AppCompatActivity {
    private EditText ETemail, ETpassword;
    private boolean userExists= false;
    private Button buttonCreateAccount;
    private Button buttonLogin;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        db = FirebaseDatabase.getInstance().getReference();
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

    }

    public void openWelcomePage() {
        userExists = false;
        final String email = ETemail.getText().toString();
        final String password = ETpassword.getText().toString();

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
            Intent intent = new Intent(activity_sign_in.this, WelcomePage.class);
            intent.putExtra("Admin", true);
            startActivity(intent);
        }
        else {
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity_sign_in.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //userInDatabase(email);
                    if(!task.isSuccessful()){ //|| !userExists){
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

    public void userInDatabase(final String email){

        Query query = db.child("users");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String emailUser = ds.child("email").getValue(String.class);
                    //Toast.makeText(activity_sign_in.this, emailUser, Toast.LENGTH_SHORT).show();
                    //Log.println(Log.DEBUG,"email", emailUser);
                    if(emailUser.equals(email)) {
                        userExists = true;
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });

        //return userExists;
    }

}

