package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.finalproject.BookAppointmentDay;
import com.example.finalproject.FindClinic;
import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.Role;
import com.example.model.WalkInClinic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

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
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
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
                                    clinicRating.setRating((float)clinic.getSumRated()/clinic.getNumRated());
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

    public void openactivity_bookApointment(){
        Intent intent = new Intent(this, BookAppointmentDay.class);
        startActivity(intent);
    }
}
