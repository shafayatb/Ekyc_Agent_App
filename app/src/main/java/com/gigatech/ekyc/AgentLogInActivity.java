package com.gigatech.ekyc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gigatech.ekyc.model.OtpResponse;
import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;
import com.gigatech.ekyc.utils.SharedPreferenceClass;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class AgentLogInActivity extends AppCompatActivity {

    RetrofitApiCall retrofitApiCall;
    Call<ResponseBody> getUserDetails;
    EditText phoneEt;
    Button goButton;
    Button backButton_agentLogIn;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_log_in);

        phoneEt = findViewById(R.id.phoneEtId);
        goButton = findViewById(R.id.goButtonId);
        backButton_agentLogIn = findViewById(R.id.backButtonId_agentLogIn);

        retrofitApiCall = RetroFitInstance.retrofitInstance().create(RetrofitApiCall.class);


        goButton.setOnClickListener(this::gotoButtonClick);

        backButton_agentLogIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), WelcomeActivity.class)));

        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
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


    void gotoButtonClick(View gotoButton) {

        if (phoneEt.getText().toString().isEmpty()) {

            Toast.makeText(getApplicationContext(), "Phone number required", Snackbar.LENGTH_LONG).show();

        } else {
            goButton.setEnabled(false);
            goButton.setTextColor(Color.parseColor("#9B9B9B"));
            disposable.add(retrofitApiCall.getOtp(phoneEt.getText().toString(), "agent")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<OtpResponse>(){

                        @Override
                        public void onSuccess(OtpResponse otpResponse) {
                            goButton.setEnabled(true);
                            goButton.setTextColor(Color.parseColor("#f9f9f9"));
                            if(otpResponse.getStatus().equals("success")){
                                SharedPreferenceClass.saveVal(getApplicationContext(), "agentNumber", phoneEt.getText().toString());
                                startActivity(new Intent(getApplicationContext(), AgentVerificationActivity.class));
                            } else if(otpResponse.getStatus().equals("failed")){
                                Toast.makeText(AgentLogInActivity.this, "Mobile number is not valid", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            goButton.setEnabled(true);
                            goButton.setTextColor(Color.parseColor("#f9f9f9"));
                            Toast.makeText(AgentLogInActivity.this, "Mobile number is not valid", Toast.LENGTH_LONG).show();
                        }
                    }));



        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
