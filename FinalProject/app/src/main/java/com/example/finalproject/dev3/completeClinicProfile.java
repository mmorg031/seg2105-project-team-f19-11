package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.WelcomePage;
import com.example.finalproject.activity_sign_in;
import com.example.model.Employee;
import com.example.model.Person;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.regex.Pattern;

public class completeClinicProfile extends AppCompatActivity {
    private EditText nameText;
    private EditText addressText;
    private EditText phoneText;
    private ImageButton save;
    private CheckBox debit;
    private CheckBox credit;
    private CheckBox cash;
    private CheckBox ohip;
    private CheckBox uhip;
    private CheckBox privateIns;
    private CheckBox noIns;
    private static boolean doneProfile=false;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_clinic_profile);

        nameText = (EditText) findViewById(R.id.nameText);
        addressText = (EditText) findViewById(R.id.addressText);
        phoneText = (EditText) findViewById(R.id.phoneText);
        save = (ImageButton) findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClinicInfo();
            }
        });

        debit = (CheckBox) findViewById(R.id.debit);
        credit = (CheckBox) findViewById(R.id.credit);
        cash = (CheckBox) findViewById(R.id.cash);
        ohip = (CheckBox) findViewById(R.id.ohip);
        uhip = (CheckBox) findViewById(R.id.uhip);
        privateIns = (CheckBox) findViewById(R.id.privateInsurance);
        noIns = (CheckBox) findViewById(R.id.noInsurance);
    }

    public  Map<String,Boolean> getPaymentMap(WalkInClinic clinic){
        Map<String,Boolean> payments = clinic.getPayments();
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
            clinic.setPayments(payments);
            return payments;
        }
        else{
            return null;
        }
    }

    public  Map<String,Boolean> getInsuranceMap(WalkInClinic clinic){
        Map<String,Boolean> insurances = clinic.getInsurances();
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
            clinic.setInsurances(insurances);
            return insurances;
        }
        else{
            return null;
        }
    }

    public void saveClinicInfo(){
        final String name = nameText.getText().toString();
        final String address = addressText.getText().toString();
        final String phone = phoneText.getText().toString();


        if(name.isEmpty()){
            nameText.setError("Please Provide a Name");
            nameText.requestFocus();
        }
        else if(address.isEmpty()){
            addressText.setError("Please Provide an Address");
            addressText.requestFocus();
        }
        else if(phone.isEmpty() || !Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}").matcher(phone).matches()){
            phoneText.setError("Please provide a Phone Number in form: 1234567890, 123-456-7890, or (123)456-7890");
            phoneText.requestFocus();
        }
        else {
            final WalkInClinic clinic = new WalkInClinic(name, address, phone);
            Map<String, Boolean> insurance = getInsuranceMap(clinic);
            Map<String, Boolean> payments = getPaymentMap(clinic);

            if (insurance != null && payments != null) {
                final String userID = mFirebaseAuth.getInstance().getCurrentUser().getUid();
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference("users/" + userID);

                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                            if (!doneProfile) {
                                Employee user = dataSnapshot.getValue(Employee.class);
                                //db.removeValue();
                                user.setClinic(clinic);
                                db.setValue(user);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }


                });

                Intent intent = new Intent(this, WelcomePage.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(completeClinicProfile.this, "Please check a payment and insurance method!", Toast.LENGTH_SHORT).show();

            }
        }
        
    }



}
