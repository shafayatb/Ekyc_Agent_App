package com.gigatech.ekyc;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentLogInActivity extends AppCompatActivity {

    RetrofitApiCall retrofitApiCall;
    Call<ResponseBody> getUserDetails;
    EditText phoneEt;
    Button goButton;
    Button backButton_agentLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_log_in);

        phoneEt = findViewById(R.id.phoneEtId);
        goButton = findViewById(R.id.goButtonId);
        backButton_agentLogIn = findViewById(R.id.backButtonId_agentLogIn);

        retrofitApiCall = RetroFitInstance.retrofitInstance().create(RetrofitApiCall.class);


        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoButtonClick(v);


            }
        });

        backButton_agentLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));

            }
        });

        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() ==11){
                    goButton.setEnabled(true);
                    goButton.setTextColor(Color.parseColor("#f9f9f9"));
                } else {
                    goButton.setEnabled(false);
                    goButton.setTextColor(Color.parseColor("#9B9B9B"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    void gotoButtonClick(View gotoButton){

        if (phoneEt.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(),"Phone number required",Snackbar.LENGTH_LONG).show();
//            Snackbar.make(findViewById(R.id.snackbar_text),"Phone number required",Snackbar.LENGTH_LONG).show();

        }else {

            getUserDetails=retrofitApiCall.getLoginDetails("testuser","123456");
            getUserDetails.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.v("RESULT",response.toString());

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.v("RESULT",call.toString());
                }
            });
            SharedPreferenceClass.saveVal(getApplicationContext(),"agentNumber",phoneEt.getText().toString());
            startActivity(new Intent(getApplicationContext(), AgentVerificationActivity.class));
        }
    }
}
