package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.WalkInClinic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DayTimeResults extends AppCompatActivity {
    private ImageButton backButton;
    private ListView results;
    private ArrayList<HashMap<String,String>> clinicResult= new ArrayList<HashMap<String, String>>();
    private TextView forWhat;
    private String time;
    private String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_time_results);

        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_searchWorkingHours();
            }
        });

        results = findViewById(R.id.clinicListTimes);

        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        time = intent.getStringExtra("time");
        forWhat = findViewById(R.id.serviceListText);
        forWhat.setText("For "+day+" at "+time);

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

                                if(clinic.isWithinWorkingHours(day,time)){
                                    String name = clinic.getName();
                                    String address = clinic.getLocation();
                                    HashMap<String, String> datum = new HashMap<String, String>();
                                    datum.put("Name", name);
                                    datum.put("Address", address);
                                    clinicResult.add(datum);
                                }
                            }
                        }


                        SimpleAdapter adapter = new SimpleAdapter(DayTimeResults.this,clinicResult,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Address"}, new int[]{android.R.id.text1, android.R.id.text2});
                        results.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String name = listPosition.get("Name");
                String address = listPosition.get("Address");
                openactivity_rating(name,address);

            }
        });

    }

    public void openactivity_searchWorkingHours(){
        Intent intent = new Intent(this, SearchWorkingHours.class);
        startActivity(intent);
    }

    public void openactivity_rating(String name, String address){
        if(!name.isEmpty() && !address.isEmpty()){
            Intent intent = new Intent(this, BookingAppmnt.class);
            intent.putExtra("name",name);
            intent.putExtra("address",address);
            startActivity(intent);
        }
        else{
            Toast.makeText(DayTimeResults.this, "Please select a valid clinic name and address", Toast.LENGTH_SHORT).show();
        }
    }

}
