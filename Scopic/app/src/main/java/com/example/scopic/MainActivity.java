package com.example.scopic;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void haveAccountLoginClicked(View v) {
        setContentView(R.layout.activity_sign_in);
        // This is the function that is called from activity_sign_up.xml and line 20 basically tells
        // the program to switch to "activity_sign_in" when the haveAccountLoginClicked is called
        // when the button is clicked
    }
    public void createAccountClicked(View v) {
        setContentView(R.layout.activity_sign_up);
    }
}
