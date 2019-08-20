package com.gigatech.ekyc;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gigatech.ekyc.model.OtpResponse;
import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;
import com.gigatech.ekyc.utils.SharedPreferenceClass;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AgentVerificationActivity extends AppCompatActivity {

    Button nextButton;
    Button backButton_AgentVerification;
    TextView resendCodeTv;
    EditText otpVerifyEditText;
    RetrofitApiCall retrofitApiCall;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_verification);

        nextButton = findViewById(R.id.nextButtonId);
        backButton_AgentVerification = findViewById(R.id.backButtonId_AgentVerification);
        resendCodeTv = findViewById(R.id.resendCodeTvId);
        otpVerifyEditText = findViewById(R.id.otpVerifyEditText);

        retrofitApiCall = RetroFitInstance.retrofitInstance().create(RetrofitApiCall.class);

        otpVerifyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 4) {
                    nextButton.setEnabled(true);
                    nextButton.setTextColor(Color.parseColor("#f9f9f9"));
                } else {
                    nextButton.setEnabled(false);
                    nextButton.setTextColor(Color.parseColor("#9B9B9B"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nextButton.setOnClickListener(v -> {

            disposable.add(retrofitApiCall.getOtp(otpVerifyEditText.getText().toString(), "agent")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<OtpResponse>(){

                        @Override
                        public void onSuccess(OtpResponse otpResponse) {
                            nextButton.setEnabled(true);
                            nextButton.setTextColor(Color.parseColor("#f9f9f9"));
                            if(otpResponse.getStatus().equals("success")){
                                SharedPreferenceClass.saveVal(getApplicationContext(), "agentToken", otpResponse.getToken());
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            } else if(otpResponse.getStatus().equals("failed")){
                                Toast.makeText(AgentVerificationActivity.this, "Sorry could not verify.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            nextButton.setEnabled(true);
                            nextButton.setTextColor(Color.parseColor("#f9f9f9"));
                            Toast.makeText(AgentVerificationActivity.this, "Sorry could not verify.", Toast.LENGTH_LONG).show();
                        }
                    }));


        });

        backButton_AgentVerification.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),AgentLogInActivity.class)));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
