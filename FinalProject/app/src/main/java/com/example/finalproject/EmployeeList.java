package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
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

public class
EmployeeList extends AppCompatActivity {

    private ImageButton buttonBack ;
    private Button patientButton ;
    private ListView employeeList;
    private ArrayList<HashMap<String,String>> employeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        patientButton = (Button) findViewById(R.id.patientButton);
        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_PatientList();
            }
        });

        employeeList = (ListView) findViewById(R.id.employeeList);
        employeeData = new ArrayList<HashMap<String, String>>();

        buttonBack = (ImageButton) findViewById(R.id.backBtnEmp);
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
                            if(role.equals("Employee")) {
                                String name = ds.child("name").getValue(String.class);
                                String email = ds.child("email").getValue(String.class);
                                HashMap<String, String> datum = new HashMap<String, String>();
                                datum.put("Name", name);
                                datum.put("Email", email);
                                employeeData.add(datum);
                            }
                        }


                        SimpleAdapter adapter = new SimpleAdapter(EmployeeList.this,employeeData,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Email"}, new int[]{android.R.id.text1, android.R.id.text2});
                        employeeList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

        //employeeList.setOnItemClickListener(openactivity_popup());
        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String em = listPosition.get("Email");
                OpenPopUp(em);

            }
        });
    }


    public void openactivity_PatientList(){
        Intent intent = new Intent(this, PatientList.class);
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