package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalproject.FindClinic;
import com.example.finalproject.R;
import com.example.finalproject.activity_sign_in;

import static com.example.model.WorkingHours.isCorrectTime;

public class SearchWorkingHours extends AppCompatActivity {
    private ImageButton back;
    private Button search;
    private Spinner day;
    private EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_working_hours);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_back();
            }
        });


        search = findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_search();
            }
        });

        day = findViewById(R.id.chooseDay);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter);


        time = findViewById(R.id.timeEntered);


    }

    public void openactivity_back(){
        Intent intent = new Intent(this, FindClinic.class);
        startActivity(intent);
    }

    public void openactivity_search(){
        String timeEntered = time.getText().toString();
        String dayEntered = day.getSelectedItem().toString();

        if(!timeEntered.isEmpty() && !dayEntered.isEmpty() && isCorrectTime(timeEntered) ) {
            Intent intent = new Intent(this, DayTimeResults.class);
            intent.putExtra("day",dayEntered);
            intent.putExtra("time",timeEntered);
            startActivity(intent);
        }
        else{
            Toast.makeText(SearchWorkingHours.this, "Please select a valid time and day! Time should be in form 00:00", Toast.LENGTH_SHORT).show();
        }
    }
}
