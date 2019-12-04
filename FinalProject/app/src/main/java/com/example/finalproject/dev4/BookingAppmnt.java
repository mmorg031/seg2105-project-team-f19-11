package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.Patient;
import com.example.model.Role;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.makeText;

public class BookingAppmnt extends AppCompatActivity {
    private ImageButton backButton;
    private String name;
    private String address;
    private TextView clinicName;
    private TextView clinicAddress;
    private ListView clinicService;
    private RatingBar clinicRating;
    private Button bookApt;
    private ArrayList<HashMap<String,String>> serviceOffered= new ArrayList<HashMap<String, String>>();
    private String mondayHours;
    private String tuesdayHours;
    private String wednesdayHours;
    private String thursdayHours;
    private String fridayHours;
    private String saturdayHours;
    private String sundayHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appmnt);

        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToFindClinicPage();
            }
        });
        bookApt = findViewById(R.id.button);
        bookApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_bookApointment();
            }
        });
        clinicName = findViewById(R.id.bookClinicName);
        clinicAddress= findViewById(R.id.bookClinicAddress);
        clinicService = findViewById(R.id.bookServicesOfferedList);
        clinicRating = findViewById(R.id.currentClinicRating);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        clinicName.setText(name);
        address = intent.getStringExtra("address");
        clinicAddress.setText(address);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.err.println(dataSnapshot.getChildrenCount());
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String role = ds.child("role").getValue(String.class);
                            if(role.equals("Employee")) {
                                Employee user = ds.getValue(Employee.class);
                                WalkInClinic clinic = user.getClinic();

                                if(clinic.getName().equals(name) && clinic.getLocation().equals(address)){
                                    for(Map.Entry<String, Role> entry : clinic.getServices().entrySet()){
                                        HashMap<String, String> datum = new HashMap<String, String>();
                                        datum.put("Name", entry.getKey());
                                        datum.put("Role", entry.getValue().toString());
                                        serviceOffered.add(datum);
                                    }
                                    clinicRating.setIsIndicator(true);
                                    clinicRating.setRating((float)clinic.getSumRated()/clinic.getNumRated());

                                    mondayHours= clinic.returnWorkingHoursString("Monday");
                                    tuesdayHours = clinic.returnWorkingHoursString("Tuesday");
                                    wednesdayHours = clinic.returnWorkingHoursString("Wednesday");
                                    thursdayHours = clinic.returnWorkingHoursString("Thursday");
                                    fridayHours = clinic.returnWorkingHoursString("Friday");
                                    saturdayHours =clinic.returnWorkingHoursString("Saturday");
                                    sundayHours = clinic.returnWorkingHoursString("Sunday");

                                    break;
                                    //return;
                                }
                            }
                        }


                        SimpleAdapter adapter = new SimpleAdapter(BookingAppmnt.this,serviceOffered,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Role"}, new int[]{android.R.id.text1, android.R.id.text2});
                        clinicService.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});
    }


    public void openactivity_goToFindClinicPage(){
        Intent intent = new Intent(this, FindClinic.class);
        startActivity(intent);
    }

    public void openactivity_goToBookingDay(){
        Intent intent = new Intent(this, BookAppointmentDay.class);
        intent.putExtra("name", name);
        intent.putExtra("address", address);
        intent.putExtra("mondayHours", mondayHours);
        intent.putExtra("tuesdayHours", tuesdayHours);
        intent.putExtra("wednesdayHours", wednesdayHours);
        intent.putExtra("thursdayHours", thursdayHours);
        intent.putExtra("fridayHours", fridayHours);
        intent.putExtra("saturdayHours", saturdayHours);
        intent.putExtra("sundayHours", sundayHours);
        startActivity(intent);
    }

    public void openactivity_goToRate(){
        Intent intent = new Intent(this, RateClinic.class);
        intent.putExtra("clinicName", name);
        intent.putExtra("clinicAddress",address);
        startActivity(intent);
    }

    public void openactivity_bookApointment(){

        String uid = FirebaseAuth.getInstance().getUid().toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Patient user = dataSnapshot.getValue(Patient.class);

                        if(!user.getLastRate()){
                            openactivity_goToBookingDay();
                        }
                        else{
                            String[] anApptParts = user.getAppointmentTime().split("#");
                            String date = anApptParts[0];
                            String time =anApptParts[1];
                            Toast.makeText(BookingAppmnt.this, "You already have an appointment on "+date+" at "+time+" , at Clinic: "+user.getLastClinicName()+", You can book an appointment after this date!", Toast.LENGTH_SHORT).show();
                            if(user.isItTimeToRate()){
                                openactivity_goToRate();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

    }
}
