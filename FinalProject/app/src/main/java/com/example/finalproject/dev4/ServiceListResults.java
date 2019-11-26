package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.finalproject.R;

public class ServiceListResults extends AppCompatActivity {

    private ImageButton back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list_results);

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_bookingAppmnt();
            }
        });

    }
    public void openactivity_bookingAppmnt() {

        Intent intent = new Intent(this, BookingAppmnt.class);
        startActivity(intent);
    }
}
