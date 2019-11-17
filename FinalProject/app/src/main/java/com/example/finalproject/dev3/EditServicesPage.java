package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EditServicesPage extends AppCompatActivity {

    private ImageButton save2;
    private ImageButton back;
    private LinearLayout parentLayout;
    private List<String> servicesNamesOffered;
    private List<Role> servicesRolesOffered;
    private int id_checkbox=1001;
    private int offset=0;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services_page);

        save2 = (ImageButton) findViewById(R.id.saveBtn);
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_saveServices();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_servicesOfferedPage();
            }
        });

        servicesNamesOffered= new ArrayList<String>();
        servicesRolesOffered= new ArrayList<Role>();

        final LinearLayout parentLayout = (LinearLayout) findViewById(R.id.check_add_layout);
        final LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkParams.setMargins(10, 10, 10, 10);
        checkParams.gravity = Gravity.CENTER;



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("services");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String name = ds.getKey();
                            String role = ds.getValue(String.class);
                            servicesNamesOffered.add(name);
                            servicesRolesOffered.add(Role.valueOf(role));

                            CheckBox checkBox = new CheckBox(EditServicesPage.this);
                            checkBox.setId(id_checkbox+offset);
                            offset+=1;
                            checkBox.setText("Service: "+name+", Role: "+role.toString());
                            checkBox.setLayoutParams(checkParams);

                            parentLayout.addView(checkBox);

                        }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});

    }



    public void openactivity_servicesOfferedPage(){
        Intent intent = new Intent(this, ServicesOfferedPage.class);
        startActivity(intent);
    }


    public void openactivity_saveServices(){
        final Map<String,Role> newServices = new HashMap<String, Role>();
        for(int i=id_checkbox; i<id_checkbox+offset; i++){
            CheckBox cb = findViewById(i);
            if(cb.isChecked()){
                newServices.put(servicesNamesOffered.get(i-id_checkbox), servicesRolesOffered.get(i-id_checkbox));
            }

        }
        if(newServices.size()>0){
            //remove theNone key if there

            final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + userID);

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {

                        Employee user = dataSnapshot.getValue(Employee.class);
                        WalkInClinic clinic = user.getClinic();
                        clinic.setServices(newServices);
                        user.setClinic(clinic);
                        db.setValue(user);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }


            });

            openactivity_servicesOfferedPage();
        }
        else{
            Toast.makeText(EditServicesPage.this, "Please check a service offered!", Toast.LENGTH_SHORT).show();

        }

    }
}
