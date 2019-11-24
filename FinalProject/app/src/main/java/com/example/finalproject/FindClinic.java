package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.finalproject.dev3.SearchWorkingHours;

public class FindClinic extends AppCompatActivity {
    private SearchView searchBar; //the search bar where user searches clinic/address/service
    private Button searchByHours; //IMPLEMENTED; goes to new page to search by working hours

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_clinic);

        searchByHours = findViewById(R.id.searchByHoursBtn);
        searchByHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_searchWorkingHours();
            }
        });
    }

    public void openactivity_searchWorkingHours(){
        Intent intent = new Intent(this, SearchWorkingHours.class);
        startActivity(intent);
    }
}
