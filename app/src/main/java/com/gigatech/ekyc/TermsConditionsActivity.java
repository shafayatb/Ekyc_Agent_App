package com.gigatech.ekyc;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TermsConditionsActivity extends AppCompatActivity {


    TextView licence_text_view;
    Button agreeContinueButton;
    ImageButton backButtonId;
    String htmlText = "<font color=#FFFFFF size=14>• Step 1:</font>&nbsp;&nbsp; <font color=#998FA2 size=14>You may use the Services only if you agree to form a binding contract with us and are\n" +
            "        not a person barred from receiving services under the laws of the applicable jurisdiction.</font> <br/><br/>\n" +
            "        <font color=#FFFFFF size=14>• Step 2:</font>&nbsp;&nbsp;  <font color=#998FA2 size=14>Our Privacy Policy describes how we handle the information you provide to us when you use our Services.</font>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        licence_text_view = findViewById(R.id.licence_text_view);
        agreeContinueButton = findViewById(R.id.agreeContinueButtonId);
        backButtonId = findViewById(R.id.backButtonId);

        licence_text_view.setSelected(true);
        licence_text_view.setMovementMethod(new ScrollingMovementMethod());
//        step1_textView.setText(Html.fromHtml(htmlText));

        agreeContinueButton.setOnClickListener(v -> {
                    startActivity(new Intent(getApplicationContext(), AgentLogInActivity.class));
                    finish();
                }
        );

        backButtonId.setOnClickListener(view -> finish());
    }
}
