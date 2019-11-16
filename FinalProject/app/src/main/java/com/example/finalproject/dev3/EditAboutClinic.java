package com.example.finalproject.dev3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.finalproject.R;

public class EditAboutClinic extends AppCompatActivity {
    private ImageButton backButton; //goes back to about page
    private ImageButton saveButton; //saves new info

    private EditText nameOfClinic; //type new name
    private EditText addressOfClinic; //type new address
    private EditText phoneOfClinic; //type new phone number, input type = phone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_clinic);
    }
}
