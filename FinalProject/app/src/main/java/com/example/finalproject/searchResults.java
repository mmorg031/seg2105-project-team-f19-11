package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class searchResults extends AppCompatActivity {

    private ImageButton backButton; //goes back to Find Clinic page
    private SearchView searchBar; //search by clinic, address, or services again
    private TextView header; // top of page says "Search Results For <INPUT>:"
    private ListView clinicNames; //list of clinic name results
    private ListView addresses; //list of addresses results
    private ListView services; //list of services results

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToFindClinicPage();
            }
        });
    }


    public void openactivity_goToFindClinicPage(){
        Intent intent = new Intent(this, FindClinic.class);
        startActivity(intent);
    }
}
