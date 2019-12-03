package com.example.finalproject.dev4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.WelcomePage;

public class FindClinic extends AppCompatActivity {
    private SearchView searchBar; //the search bar where user searches clinic/address/service
    private Button searchByHours; //IMPLEMENTED; goes to new page to search by working hours
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_clinic);

        backButton = (ImageButton) findViewById(R.id.backBtn) ;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_goToWelcomePage();
            }
        });

        searchByHours = findViewById(R.id.searchByHoursBtn);
        searchByHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_searchWorkingHours();
            }
        });

        searchBar = (SearchView) findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                //searchBar.setT;
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                openactivity_search(query);
                return true;
            }
        });
    }

    public void openactivity_goToWelcomePage(){
        Intent intent = new Intent(this, WelcomePage.class);
        startActivity(intent);
    }

    public void openactivity_searchWorkingHours(){
        Intent intent = new Intent(this, SearchWorkingHours.class);
        startActivity(intent);
    }

    public void openactivity_search(String partialKeyword){
//        String partialKeyword = searchBar.toString();

        if(!partialKeyword.isEmpty()){
            Intent intent = new Intent(this, searchResults.class );
            intent.putExtra("keyword", partialKeyword);
            startActivity(intent);
        }
        else{
            Toast.makeText(FindClinic.this, "Please enter a partial keyword to search for clinic by name, address, or service offered", Toast.LENGTH_SHORT).show();
        }
    }
}
