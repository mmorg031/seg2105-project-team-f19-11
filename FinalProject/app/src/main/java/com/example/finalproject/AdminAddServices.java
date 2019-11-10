package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model.Role;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminAddServices extends AppCompatActivity {
    private ImageButton backButton;
    private EditText serviceName;
    private Button createButton;
    private Spinner chooseRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_services);

        chooseRole = (Spinner) findViewById(R.id.chooseRole);
        serviceName = (EditText) findViewById(R.id.writeName);



        backButton = (ImageButton) findViewById(R.id.backBtnPat);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        ArrayAdapter<Role> myAdapter = new ArrayAdapter<Role>(AdminAddServices.this,
                android.R.layout.simple_list_item_1, Role.values());
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRole.setAdapter(myAdapter);

        createButton = (Button) findViewById(R.id.button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createService();
            }
        });
    }

    public void goBack(){
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }


    public void createService(){
        final String name = serviceName.getText().toString();
        final String role = chooseRole.getSelectedItem().toString();

        if(name.isEmpty()){
            serviceName.setError("Please Provide a Name");
            serviceName.requestFocus();
        }
        else if(role.isEmpty()){
            Toast.makeText(AdminAddServices.this, "Please Choose a Role", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("services");
            Map<String,Object> newService = new HashMap<String,Object>();
            newService.put(name,role);
            db.updateChildren(newService);
            Intent intent = new Intent(this, ServicesList.class);
            startActivity(intent);
        }

    }
}
