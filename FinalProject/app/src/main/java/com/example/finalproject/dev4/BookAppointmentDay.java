package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.activity_sign_in;
import com.example.model.Employee;
import com.example.model.Patient;
import com.example.model.Role;
import com.example.model.WalkInClinic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookAppointmentDay extends AppCompatActivity {

    private ImageButton backButton; //opens the clinic info page (bookingappmnt)
    private CalendarView chooseDay; //chooses day on a calendar widget
    final static private String TAG = "CalendarActivity";
    private String clinicName;
    private String clinicAddress;
    private Patient currentUser;
    private String waitTime;
    private String Apptdate; //date of appointment selected
    private String ApptForPatient;
    private boolean appointmentMade=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_day);
        Intent intent = getIntent();
        clinicName = intent.getStringExtra("name");
        clinicAddress = intent.getStringExtra("address");

        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToFindClinicPage();
            }
        });


        chooseDay = (CalendarView) findViewById(R.id.calendarView);
        //chooseDay.setDate(Calendar.getInstance().getTimeInMillis(),false,true);
        final Calendar calendar = Calendar.getInstance();
        chooseDay.setMinDate(calendar.getTimeInMillis());

        chooseDay.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i2, int i3) {
                final String date = i3 + "/" + (i2 + 1) + "/" + i;
                Apptdate = date;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy: " + date);
                calendar.set(i, i2, i3);
                String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                openactivity_saveApptToClinic(dayOfWeek,date);
            }});

    }

    public void openactivity_goToFindClinicPage(){
        Intent intent = new Intent(this, FindClinic.class);
        startActivity(intent);
    }



    public void openactivity_goToConfirmationPage(){
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference db= FirebaseDatabase.getInstance().getReference("users/" + uid);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                    Patient user = dataSnapshot.getValue(Patient.class);
                    user.setLastClinicName(clinicName);
                    user.setLastClinicAddress(clinicAddress);
                    user.setLastRate(true);
                    user.setAppointmentTime(ApptForPatient);
                    currentUser=user;
                    db.setValue(user);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        Intent intent = new Intent(BookAppointmentDay.this, ConfirmationPage.class);
        intent.putExtra("date", Apptdate);
        intent.putExtra("waitTime", waitTime);
        startActivity(intent);
    }



    public void openactivity_saveApptToClinic(final String dayOfWeek, final String date){
        //update Clinic appt info and Patients
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.err.println("DAYBOOOK");
                        System.err.println(dataSnapshot.getChildrenCount());
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            String role = ds.child("role").getValue(String.class);
                            if(role.equals("Employee")) {
                                Employee user = ds.getValue(Employee.class);
                                WalkInClinic clinic = user.getClinic();

                                if(clinic.getName().equals(clinicName) && clinic.getLocation().equals(clinicAddress)){
                                    System.err.println(dayOfWeek);
                                    System.err.println(clinic.getWorkingHours().get(dayOfWeek).getClosed());
                                    System.err.println(clinic.getWorkingHours().get(dayOfWeek).getStartTime());
                                    if(!clinic.getWorkingHours().get(dayOfWeek).getClosed()) {
                                        System.err.println("Insideyo");
                                        Map<String,String> apptResults = clinic.updateAppointments(date, dayOfWeek, uid);
                                        if(apptResults==null){
                                            System.err.println("appt resluts were null");
                                            Toast.makeText(BookAppointmentDay.this, "Cannot make an appoint clinic will be closed", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        else {
                                            waitTime = apptResults.get("waitTime");
                                            ApptForPatient = apptResults.get("appt");
                                            user.setClinic(clinic);
                                            ref.child(ds.getKey()).setValue(user);
                                            appointmentMade = true;
                                        }
                                    }
                                    else{
                                        Toast.makeText(BookAppointmentDay.this, "Cannot make an appoint clinic will be closed", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                        }
                        System.err.println("Done");
                        System.err.println(dataSnapshot.getChildrenCount());

                        if(appointmentMade){
                            openactivity_goToConfirmationPage();
                        }
                        else{
                            Toast.makeText(BookAppointmentDay.this, "Cant schedule you for this day the clinic is closed!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError

                    }});
    }





}
