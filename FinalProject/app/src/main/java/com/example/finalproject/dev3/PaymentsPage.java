package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.finalproject.R;

public class PaymentsPage extends AppCompatActivity {

    private Button edit ;
    private ImageButton back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_page);

        edit = (Button) findViewById(R.id.editPaymentsBtn) ;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_editPaymentsPage();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_aboutPage();
            }
        });
    }

    public void openactivity_editPaymentsPage(){
        Intent intent = new Intent(this, EditPaymentsPage.class);
        startActivity(intent);
    }
    public void openactivity_aboutPage(){
        Intent intent = new Intent(this, ClinicAbout.class);
        startActivity(intent);
    }
}
