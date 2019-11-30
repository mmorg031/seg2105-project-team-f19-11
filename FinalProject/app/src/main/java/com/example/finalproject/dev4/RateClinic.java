package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.AdminChoose;
import com.example.finalproject.R;
import com.example.finalproject.WelcomePage;
import com.example.model.Employee;
import com.example.model.Patient;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class RateClinic extends AppCompatActivity {
    private ImageButton back;
    private RatingBar rating;
    private EditText comments;
    private Button submit;
    private TextView header;
    private String clinicName;
    private String clinicAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_clinic);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_goback();
            }
        });
        rating = findViewById(R.id.newRatingEntry);
        comments = findViewById(R.id.comments);
        submit= findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_submit();
            }
        });
        header = findViewById(R.id.newRatingEntryText);

        clinicName = getIntent().getStringExtra("clinicName");
        clinicAddress = getIntent().getStringExtra("clinicAddress");


    }

    public void openactivity_goback(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public void openactivity_submit(){
        final float newRating = rating.getRating();
        final String newComment = comments.getText().toString();

        if( newRating>0 && !newComment.isEmpty()) {
            //save entry

            //update Clinic appt info and Patients
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                String role = ds.child("role").getValue(String.class);
                                if(role.equals("Employee")) {
                                    Employee user = ds.getValue(Employee.class);
                                    WalkInClinic clinic = user.getClinic();

                                    if(clinic.getName().equals(clinicName) && clinic.getLocation().equals(clinicAddress)){
                                        Map<String, Integer> reviews= clinic.getReviews();
                                        reviews.put(newComment, Math.round(newRating));
                                        clinic.setReviews(reviews);
                                        clinic.setNumRated(clinic.getNumRated()+1);
                                        clinic.setSumRated(clinic.getSumRated()+Math.round(newRating));
                                        user.setClinic(clinic);
                                        ref.setValue(user);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError

                        }});

            //also update the patient so she/he doesnt rate again!
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + userID);

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                        Patient user = dataSnapshot.getValue(Patient.class);
                        user.setLastRate(false);
                        db.setValue(user);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }


            });


            Intent intent = new Intent(this, FindClinic.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(RateClinic.this, "Please Enter Rating and Comment", Toast.LENGTH_SHORT).show();
        }
    }
}
