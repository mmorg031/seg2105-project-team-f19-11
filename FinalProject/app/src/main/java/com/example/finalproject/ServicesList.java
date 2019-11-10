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

public class ServicesList extends AppCompatActivity {
    private ImageButton buttonBack ;
    private ListView serviceList;
    private ArrayList<HashMap<String,String>> serviceData;
    private ImageButton buttonAddService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        serviceList = (ListView) findViewById(R.id.servicesList);
        serviceData = new ArrayList<HashMap<String, String>>();
        buttonBack = (ImageButton) findViewById(R.id.backBtnPat);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goback();
            }
        });

        buttonAddService = (ImageButton) findViewById(R.id.addServiceBtn);
        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openactivity_AdminAddServices();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("services");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String name = ds.getKey();
                            String role = ds.getValue(String.class);
                            HashMap<String, String> datum = new HashMap<String, String>();
                            datum.put("Name", name);
                            datum.put("Role", role);
                            serviceData.add(datum);

                        }


                        SimpleAdapter adapter = new SimpleAdapter(ServicesList.this,serviceData,
                                android.R.layout.simple_list_item_2,
                                new String[]{"Name", "Role"}, new int[]{android.R.id.text1, android.R.id.text2});
                        serviceList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Map<String,String> listPosition = (Map<String, String>) parent.getItemAtPosition(position);
                String service = listPosition.get("Name");
                String role = listPosition.get("Role");
                openactivity_editService(service,role);

            }
        });

    }

    public void openactivity_goback(){
        Intent intent = new Intent(this, AdminChoose.class);
        startActivity(intent);
    }

    public  void  openactivity_editService(String service, String role){
        Intent intent = new Intent(this, edit_service.class);
        intent.putExtra("Service", service);
        intent.putExtra("Role", role);
        startActivity(intent);
        //finish();
    }

    public void openactivity_AdminAddServices(){
        Intent intent = new Intent(this, AdminAddServices.class);
        startActivity(intent);
    }


}
