package com.gigatech.ekyc;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SuccessNotification extends AppCompatActivity {

    AppCompatButton goToNewUserRegistration, goToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_notification);

        goToHome = findViewById(R.id.goToHome);
        goToNewUserRegistration = findViewById(R.id.goToNewUserRegistration);

        goToHome.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finishAffinity();
        });

        goToNewUserRegistration.setOnClickListener(v -> startActivity(new Intent(this, NidFrontSideCapture.class)));

    }
}
