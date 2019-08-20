package com.gigatech.ekyc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class WelcomeActivity extends AppCompatActivity {

    Button startButton;
    RelativeLayout relativeLayoutId_getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startButton = findViewById(R.id.startButtonId);
        relativeLayoutId_getStarted = findViewById(R.id.relativeLayoutId_getStarted);

        relativeLayoutId_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //For test purpose..this is Review Information Activity...
                startActivity(new Intent(getApplicationContext(), AgentLogInActivity.class));
               //startActivity(new Intent(getApplicationContext(), ReviewInformationActivity.class));
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
