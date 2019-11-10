package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.model.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientList extends AppCompatActivity {

    private ImageButton buttonBack ;
    private Button employeeButton ;
    private ListView patientList;
    private ArrayList<HashMap<String,String>> patientData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        employeeButton = (Button) findViewById(R.id.employeeButton);
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_EmployeeList();
            }
        });

        patientList = (ListView) findViewById(R.id.patientList);
        patientData = new ArrayList<HashMap<String, String>>();


        buttonBack = (ImageButton) findViewById(R.id.backBtnPat);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goback();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String role = ds.child("role").getValue(String.class);
                            if(role.equals("Patient")) {
                                String name = ds.child("name").getValue(String.class);
                                String email = ds.child("email").getValue(String.class);
                                HashMap<String, String> datum = new HashMap<String, String>();
                                datum.put("Name", name);
                                datum.put("Email", email);
                                patientData.add(datum);
                            }
                        }


                        SimpleAdapter adapter = new SimpleAdapter(PatientList.this,patientData,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Email"}, new int[]{android.R.id.text1, android.R.id.text2});
                        patientList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String em = listPosition.get("Email");
                OpenPopUp(em);

            }
        });


    }


    public void openactivity_EmployeeList(){
        Intent intent = new Intent(this, EmployeeList.class);
        startActivity(intent);
    }


    public void OpenPopUp(String email){
        Intent intent = new Intent(this, Pop.class);
        intent.putExtra("Email", email);
        startActivity(intent);
    }

    public void openactivity_goback(){
        Intent intent = new Intent(this, AdminChoose.class);
        startActivity(intent);
    }

}
