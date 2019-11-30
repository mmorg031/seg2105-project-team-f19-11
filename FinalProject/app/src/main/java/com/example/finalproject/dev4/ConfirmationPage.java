package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.R;

import org.w3c.dom.Text;

public class ConfirmationPage extends AppCompatActivity {

    private Button goToRateClinicButton;
    private TextView dayOfAppointment; //shows day of the booked appointment
    private TextView waitingTime; //shows waiting time of the booked appointment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        dayOfAppointment = (TextView) findViewById(R.id.dateText);
        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        dayOfAppointment.setText(date);

        goToRateClinicButton = (Button) findViewById(R.id.rateTheClinicBtn);
        //goToRateClinicButton.setVisibility(View.INVISIBLE);
        goToRateClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openactivity_rateTheClinic();
                openactivity_FindClinic();
            }
        });

        waitingTime = findViewById(R.id.waitTimeText);
        String waitTime = incomingIntent.getStringExtra("waitTime");
        waitingTime.setText("Expected Wait: "+waitTime);


    }


    public void openactivity_rateTheClinic(){
        Intent intent = new Intent(this, RateClinic.class);
        startActivity(intent);
    }

    public void openactivity_FindClinic(){
        Intent intent = new Intent(this, FindClinic.class);
        startActivity(intent);
    }
}
