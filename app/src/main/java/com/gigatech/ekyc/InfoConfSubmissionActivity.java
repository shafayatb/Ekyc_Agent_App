package com.gigatech.ekyc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gigatech.ekyc.model.NidData;
import com.gigatech.ekyc.model.NidResponse;
import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;
import com.gigatech.ekyc.utils.SharedPreferenceClass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InfoConfSubmissionActivity extends AppCompatActivity {

    Button submit_backButton, cancel_backButtonId;
    NidData nidData = new NidData();
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_conf_submission);

        submit_backButton = findViewById(R.id.submit_backButtonId);
        cancel_backButtonId = findViewById(R.id.cancel_backButtonId);

        if (getIntent().getParcelableExtra("NidData") != null) {
            nidData = getIntent().getParcelableExtra("NidData");
        }

        cancel_backButtonId.setOnClickListener(v -> finish());

        submit_backButton.setOnClickListener(v -> submitApplicantInfo());

    }

    void submitApplicantInfo() {

        File file = new File(SharedPreferenceClass.
                getVal(getApplicationContext(), "userImg"));

        Uri userUri = Uri.fromFile(file);

        RetrofitApiCall retrofitApiCall = RetroFitInstance.retrofitInstance().create(RetrofitApiCall.class);

        List<MultipartBody.Part> images = new ArrayList<>();

        images.add(prepareImage("applicant_photo", userUri));

        Map<String, String> map = new HashMap<>();

        map.put("step", "2");

        Map<String, RequestBody> applicantMap = getSubmitPostBody();

        submit_backButton.setEnabled(false);

        disposable.add(retrofitApiCall.imageUpload("Token " + SharedPreferenceClass.getVal(getApplicationContext(), "agentToken"), map,
                applicantMap, images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<NidResponse>() {

                    @Override
                    public void onSuccess(NidResponse nidResponse) {
                        submit_backButton.setEnabled(true);
                        if (nidResponse.getStatus().equals("success")) {
                            startActivity(new Intent(getApplicationContext(), SuccessNotification.class));
                        } else {
                            Toast.makeText(InfoConfSubmissionActivity.this, "Failed to sent application", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        submit_backButton.setEnabled(true);
                        Log.v("ErrorResponse", e.getLocalizedMessage());
                    }
                }));

    }

    MultipartBody.Part prepareImage(String partName, Uri fileUri) {
        File file = new File(Objects.requireNonNull(fileUri.getPath()));

        //RequestBody requestBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))), file);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    public Map<String, RequestBody> getSubmitPostBody() {

        Map<String, RequestBody> body = new HashMap<>();
        RequestBody nid_no = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getNidNo()
        );

        body.put("nid_no", nid_no);

        RequestBody dob = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getDob()
        );

        body.put("dob", dob);

        RequestBody applicant_name_eng = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getCustomerNameEng()
        );

        body.put("applicant_name_eng", applicant_name_eng);

        RequestBody applicant_name_ben = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getCustomerNameBen()
        );

        body.put("applicant_name_ben", applicant_name_ben);

        RequestBody mother_name = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getMotherName()
        );

        body.put("mother_name", mother_name);

        RequestBody father_name = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getFatherName()
        );

        body.put("father_name", father_name);

        RequestBody pres_address = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getAddress()
        );

        body.put("pres_address", pres_address);

        RequestBody perm_address = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getPermAddress()
        );

        body.put("perm_address", perm_address);

        RequestBody mobile_number = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getMobileNumber()
        );

        body.put("mobile_number", mobile_number);

        RequestBody gender = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getGender()
        );

        body.put("gender", gender);

        RequestBody profession = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getProfession()
        );

        body.put("profession", profession);

        RequestBody nominee = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getNominee()
        );

        body.put("nominee", nominee);

        RequestBody nominee_relation = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getNomineeRelation()
        );

        body.put("nominee_relation", nominee_relation);

        RequestBody id_front_url = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getIdFrontUrl()
        );

        body.put("id_front_url", id_front_url);

        RequestBody id_back_url = RequestBody.create(
                MediaType.parse("text/plain"),
                nidData.getIdBackUrl()
        );

        body.put("id_back_url", id_back_url);


        return body;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
