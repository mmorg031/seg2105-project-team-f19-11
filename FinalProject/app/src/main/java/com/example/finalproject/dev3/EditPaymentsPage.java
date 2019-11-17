package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.finalproject.R;
import com.example.model.Employee;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EditPaymentsPage extends AppCompatActivity {

    private ImageButton save2 ;
    private ImageButton back ;

    private CheckBox debit;
    private CheckBox credit;
    private CheckBox cash;
    private CheckBox ohip;
    private CheckBox uhip;
    private CheckBox privateIns;
    private CheckBox noIns;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payments_page);

        debit = (CheckBox) findViewById(R.id.debit);
        credit = (CheckBox) findViewById(R.id.credit);
        cash = (CheckBox) findViewById(R.id.cash);
        ohip = (CheckBox) findViewById(R.id.ohip);
        uhip = (CheckBox) findViewById(R.id.uhip);
        privateIns = (CheckBox) findViewById(R.id.privateInsurance);
        noIns = (CheckBox) findViewById(R.id.noInsurance);

        save2 = (ImageButton) findViewById(R.id.saveBtn) ;
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_savePaymentChanges();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_paymentsPage1();
            }
        });

        Map<String, Boolean> payments = (HashMap<String, Boolean>)getIntent().getSerializableExtra("payments");
        Map<String, Boolean> insurances = (HashMap<String, Boolean>)getIntent().getSerializableExtra("insurances");
        Iterator hmIterator = payments.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)hmIterator.next();
            if(entry.getValue()== Boolean.TRUE){
                String key = (String) entry.getKey();
                if(key.equals("Debit"))
                    debit.setChecked(true);
                else if(key.equals("Credit"))
                    credit.setChecked(true);
                else if (key.equals("Cash"))
                    cash.setChecked(true);
            }

        }


        hmIterator = insurances.entrySet().iterator();

        while (hmIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)hmIterator.next();
            if(entry.getValue()== Boolean.TRUE){
                String key = (String) entry.getKey();
                if(key.equals("OHIP"))
                    ohip.setChecked(true);
                else if(key.equals("UHIP"))
                    uhip.setChecked(true);
                else if (key.equals("Private Insurance"))
                    privateIns.setChecked(true);
                else if (key.equals("No Insurance"))
                    noIns.setChecked(true);
            }

        }


    }


    public void openactivity_paymentsPage1(){
        Intent intent = new Intent(this, PaymentsPage.class);
        startActivity(intent);
    }


    public  Map<String,Boolean> getPaymentMap(){
        Map<String,Boolean> payments = new WalkInClinic().getPayments();
        boolean atleastOneChecked=false;
        if (cash.isChecked()){
            payments.put(cash.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }
        if (debit.isChecked()){
            payments.put(debit.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }
        if (credit.isChecked()){
            payments.put(credit.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }

        if(atleastOneChecked){
            //clinic.setPayments(payments);
            return payments;
        }
        else{
            return null;
        }
    }

    public  Map<String,Boolean> getInsuranceMap(){
        Map<String,Boolean> insurances = new WalkInClinic().getInsurances();
        boolean atleastOneChecked=false;

        if (ohip.isChecked()){
            insurances.put(ohip.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }
        if (uhip.isChecked()){
            insurances.put(uhip.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }
        if (privateIns.isChecked()){
            insurances.put(privateIns.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }
        if (noIns.isChecked()){
            insurances.put(noIns.getText().toString(),  Boolean.TRUE);
            atleastOneChecked=true;
        }


        if(atleastOneChecked){
            //clinic.setInsurances(insurances);
            return insurances;
        }
        else{
            return null;
        }
    }



    public void openactivity_savePaymentChanges() {

        final Map<String, Boolean> insurance = getInsuranceMap();
        final Map<String, Boolean> payments = getPaymentMap();

        if (insurance != null && payments != null) {
            final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + userID);

            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapShot : dataSnapshot.getChildren()) {

                        Employee user = dataSnapshot.getValue(Employee.class);
                        WalkInClinic clinic = user.getClinic();
                        clinic.setPayments(payments);
                        clinic.setInsurances(insurance);
                        user.setClinic(clinic);
                        db.setValue(user);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }


            });

            Intent intent = new Intent(this, PaymentsPage.class);
            startActivity(intent);
        }
    }
}
