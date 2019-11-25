package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.finalproject.R;

public class BookAppointmentDay extends AppCompatActivity {

    private ImageButton backButton; //opens the clinic info page (bookingappmnt)
    private Button bookAppointmentButton;//SAVES APPOINTMENT INFO, & goes to confirmed booking page
    private CalendarView chooseDay; //chooses day on a calendar widget
    private TimePicker chooseTime; //chooses time on a time picker spinner widget
    //choose time is currently on am/pm, I could try changing to 24 hours if easier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_day);

        bookAppointmentButton = (Button) findViewById(R.id.bookBtn);
        bookAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_bookTheAppointment();
            }
        });

        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToClinicPage();
            }
        });

    }

    public void openactivity_goToClinicPage(){
        Intent intent = new Intent(this, BookingAppmnt.class);
        startActivity(intent);
    }

    public void openactivity_bookTheAppointment(){
        Intent intent = new Intent(this, ConfirmationPage.class);
        startActivity(intent);
    }
}
