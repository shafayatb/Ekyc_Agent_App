package com.gigatech.ekyc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AgentVerificationActivity extends AppCompatActivity {

    Button nextButton;
    Button backButton_AgentVerification;
    TextView resendCodeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_verification);

        nextButton = findViewById(R.id.nextButtonId);
        backButton_AgentVerification = findViewById(R.id.backButtonId_AgentVerification);
        resendCodeTv = findViewById(R.id.resendCodeTvId);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomeActivity.class));

            }
        });

        backButton_AgentVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),AgentLogInActivity.class));

            }
        });

    }
}
