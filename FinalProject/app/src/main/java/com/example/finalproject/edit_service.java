package com.example.finalproject;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class edit_service extends AppCompatActivity {
    private EditText serviceName;
    private Spinner chooseRole;
    private ImageButton save;
    private Button deleteService;
    private ImageButton back;
    private DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        db =FirebaseDatabase.getInstance().getReference().child("clinics");

        chooseRole = (Spinner) findViewById(R.id.chooseRole);
        serviceName = (EditText) findViewById(R.id.writeName);

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
                deleteService();
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
            //Role r = new Role(role);
            //need clinic name and previous clinic name//role ,, remove and add service
           /* deleteService()
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("clinics").child(name);
            Map<String,Object> post = new HashMap<String, Role>();
            post.put(name,role);
            db.updateChildren(post); or setvalue*/

        }

    }

    public void deleteService(){
        //need clinc name and service name
        /*
        db.orderByKey().equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    String key = postsnapshot.getKey();
                    dataSnapshot.getRef().removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }*/
    }


}
