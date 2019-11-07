package com.example.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

class Pop extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);
        DisplayMetrics d = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(d);
        int w = d.widthPixels ;
        int h = d.heightPixels ;
        getWindow().setLayout((int)(w*.8), (int)(h*.6));
    }
}
