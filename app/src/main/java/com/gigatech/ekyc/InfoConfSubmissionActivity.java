package com.gigatech.ekyc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoConfSubmissionActivity extends AppCompatActivity {

    Button submit_backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_conf_submission);

        submit_backButton = findViewById(R.id.submit_backButtonId);

        submit_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),SuccessNotification.class));

            }
        });

    }
}
