package com.gigatech.ekyc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TermsConditionsActivity extends AppCompatActivity {



    TextView licence_text_view;
    Button agreeContinueButton;
    String htmlText = "<font color=#FFFFFF size=14>• Step 1:</font>&nbsp;&nbsp; <font color=#998FA2 size=14>You may use the Services only if you agree to form a binding contract with us and are\n" +
            "        not a person barred from receiving services under the laws of the applicable jurisdiction.</font> <br/><br/>\n" +
            "        <font color=#FFFFFF size=14>• Step 2:</font>&nbsp;&nbsp;  <font color=#998FA2 size=14>Our Privacy Policy describes how we handle the information you provide to us when you use our Services.</font>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        licence_text_view = findViewById(R.id.licence_text_view);
        agreeContinueButton = findViewById(R.id.agreeContinueButtonId);

        licence_text_view.setSelected(true);
        licence_text_view.setMovementMethod(new ScrollingMovementMethod());
//        step1_textView.setText(Html.fromHtml(htmlText));

        agreeContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), NidFrontSideCapture.class));

            }
        });
    }
}
