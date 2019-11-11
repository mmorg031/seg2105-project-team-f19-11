package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model.Role;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class edit_service extends AppCompatActivity {
    private EditText serviceName;
    private Spinner chooseRole;
    private ImageButton save;
    private Button deleteService;
    private ImageButton back;
    private DatabaseReference db;
    private String previousRole;
    private String previousServiceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        db =FirebaseDatabase.getInstance().getReference();

        previousRole = getIntent().getExtras().getString("Role");
        previousServiceName = getIntent().getExtras().getString("Service");

        chooseRole = (Spinner) findViewById(R.id.chooseRole);
        serviceName = (EditText) findViewById(R.id.writeName);
        serviceName.setText(previousServiceName);

        save = (ImageButton) findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        deleteService = (Button) findViewById(R.id.button);
        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_serviceList();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtnPat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        ArrayAdapter<Role> myAdapter = new ArrayAdapter<Role>(edit_service.this,
                android.R.layout.simple_list_item_1, Role.values());
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRole.setAdapter(myAdapter);

        int spinnerPosition = myAdapter.getPosition(Role.valueOf(previousRole));
        chooseRole.setSelection(spinnerPosition);

    }



    public void goBack(){
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }

    public void saveChanges(){
        final String name = serviceName.getText().toString();
        final String role = chooseRole.getSelectedItem().toString();

        if(name.isEmpty()){
            serviceName.setError("Please Provide a Name");
            serviceName.requestFocus();
        }
        else if(role.isEmpty()){
            Toast.makeText(edit_service.this, "Please Choose a Role", Toast.LENGTH_SHORT).show();
        }
        else {
            final Role r = Role.valueOf(role);
            if(!previousRole.equals(role) && !previousServiceName.equals(name)){
                editServiceName(name,r);
                editServiceRole(name,r);
            }
            else if(!previousRole.equals(role) && previousServiceName.equals(name)){
                editServiceRole(name, r);
            }
            else if(previousRole.equals(role) && !previousServiceName.equals(name)){
                editServiceName( name,  r);
            }

            Intent intent = new Intent(this, ServicesList.class);
            startActivity(intent);

        }

    }

    public void deleteService(String service, String role){
        Query query = db.child("services").orderByKey().equalTo(service);

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
    }



    public void editServiceName(String newName, Role role){
        deleteService(previousServiceName, previousRole);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("services");
        Map<String,Object> newService = new HashMap<String,Object>();
        newService.put(newName,role);
        db.updateChildren(newService);
    }

    public void editServiceRole(String name, final Role newRole){
        Query query = db.child("services").orderByKey().equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().setValue(newRole);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }


    public void openactivity_serviceList(){
        deleteService(previousServiceName, previousRole);
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }


}
