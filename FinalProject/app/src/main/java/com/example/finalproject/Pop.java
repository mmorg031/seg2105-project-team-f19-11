package com.example.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;

import static android.content.ContentValues.TAG;

public class Pop extends AppCompatActivity {
    private Button yesButton;
    private Button noButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        /*DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        int w = d.widthPixels ;
        int h = d.heightPixels ;
        getWindow().setLayout((int)(w*.8), (int)(h*.6));*/


        yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_deleteUser(getIntent().getExtras().getString("Email"));
            }
        });

        noButton = (Button) findViewById(R.id.noButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_goback();
            }
        });
    }


    public void openactivity_deleteUser(String emailToDelete){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("users").orderByChild("email").equalTo(emailToDelete);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
        /*FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //deleteUser(uid);

        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Pop.this, "User Succesfully Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Pop.this, "Could not delete User", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        Intent intent = new Intent(this, PatientList.class);
        startActivity(intent);
    }




    public void openactivity_goback(){
        finish();
    }

}
