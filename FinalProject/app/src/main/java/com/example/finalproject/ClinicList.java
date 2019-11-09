package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ClinicList extends AppCompatActivity {
    private ImageButton buttonBack ;
    private ListView clinicList;
    private ArrayList<HashMap<String,String>> clinicData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_list);

        clinicList = (ListView) findViewById(R.id.clinicList);
        clinicData = new ArrayList<HashMap<String, String>>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("clinics");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String name = ds.getKey();
                            String location = ds.child("location").getValue(String.class);
                            HashMap<String, String> datum = new HashMap<String, String>();
                            datum.put("Name", name);
                            datum.put("Location", location);
                            clinicData.add(datum);

                        }


                        SimpleAdapter adapter = new SimpleAdapter(ClinicList.this,clinicData,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Location"}, new int[]{android.R.id.text1, android.R.id.text2});
                        clinicList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

        clinicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Map<String,String> clinic =  (Map<String,String>) parent.getItemAtPosition(position);
                openactivity_serviceslist(clinic.get("Name"));
            }
        });

    }


    public void openactivity_serviceslist(String clinic){
        Intent intent = new Intent(ClinicList.this, ServicesList.class);
        intent.putExtra("clinic", clinic);
        startActivity(intent);
    }
}
