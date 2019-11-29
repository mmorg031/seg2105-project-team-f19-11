package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.finalproject.R;

import java.util.Calendar;

public class BookAppointmentDay extends AppCompatActivity {

    private ImageButton backButton; //opens the clinic info page (bookingappmnt)
    private CalendarView chooseDay; //chooses day on a calendar widget
    final static private String TAG = "CalendarActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment_day);

        chooseDay = (CalendarView) findViewById(R.id.calendarView);
        //chooseDay.setDate(Calendar.getInstance().getTimeInMillis(),false,true);

        Calendar calendar = Calendar.getInstance();
        chooseDay.setMinDate(calendar.getTimeInMillis());

        chooseDay.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i2, int i3){
                String date = i3 + "/" + (i2+1) + "/" + i;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy: " + date);

                Intent intent = new Intent(BookAppointmentDay.this, ConfirmationPage.class);
                intent.putExtra("date", date);
                startActivity(intent);
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

}
