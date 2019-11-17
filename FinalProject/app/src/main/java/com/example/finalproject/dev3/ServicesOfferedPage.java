package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.finalproject.R;
import com.example.finalproject.ServicesList;
import com.example.model.Employee;
import com.example.model.Role;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesOfferedPage extends AppCompatActivity {

    private Button edit ;
    private ImageButton back ;
    private ListView serviceList;
    private ArrayList<HashMap<String,String>> serviceData;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_offered_page);

        edit = (Button) findViewById(R.id.editServicesBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_editServicesPage();
            }
        });
        back = (ImageButton) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_aboutPage();
            }
        });
        serviceList = (ListView) findViewById(R.id.servicesOfferedList);
        serviceData = new ArrayList<HashMap<String, String>>();

        String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + userID);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Employee user = dataSnapshot.getValue(Employee.class);
                    Map<String, Role> services = user.getClinic().getServices();

                    if (!services.containsKey("None")) {
                        for (Map.Entry<String, Role> entry : services.entrySet()) {
                            HashMap<String, String> datum = new HashMap<String, String>();
                            datum.put("Name", entry.getKey());
                            datum.put("Role", entry.getValue().toString());
                            serviceData.add(datum);
                        }

                        SimpleAdapter adapter = new SimpleAdapter(ServicesOfferedPage.this, serviceData,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Role"}, new int[]{android.R.id.text1, android.R.id.text2});
                        serviceList.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void openactivity_editServicesPage(){
        Intent intent = new Intent(this, EditServicesPage.class);
        startActivity(intent);
    }
    public void openactivity_aboutPage(){
        Intent intent = new Intent(this, ClinicAbout.class);
        startActivity(intent);
    }
}
