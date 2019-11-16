package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.finalproject.R;

public class EditPaymentsPage extends AppCompatActivity {

    private Button save1 ;
    private ImageButton save2 ;
    private ImageButton back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payments_page);

        // DON'T KNOW HOW TO IMPLEMENT THE ACTUAL SAVE FUNCTIONALITY -S.M.

        save1 = (Button) findViewById(R.id.savePaymentsBtn) ;
        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_paymentsPage2();
            }
        });

        save2 = (ImageButton) findViewById(R.id.saveBtn) ;
        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_paymentsPage2();
            }
        });

        back = (ImageButton) findViewById(R.id.backBtn) ;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity_paymentsPage1();
            }
        });
    }
    public void openactivity_paymentsPage1(){
        Intent intent = new Intent(this, PaymentsPage.class);
        startActivity(intent);
    }
    public void openactivity_paymentsPage2(){
        Intent intent = new Intent(this, PaymentsPage.class);
        startActivity(intent);
    }
}
