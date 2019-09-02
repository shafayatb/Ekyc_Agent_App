package com.gigatech.ekyc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gigatech.ekyc.model.NidResponse;
import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;
import com.gigatech.ekyc.utils.ImageUtils;
import com.gigatech.ekyc.utils.SharedPreferenceClass;
import com.google.android.material.card.MaterialCardView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

public class NIDImageConfirm extends AppCompatActivity {

    Button review_confirm_button, backNidReview_ButtonId;
    ImageView imageViewId_nidFront, imageViewId_nidback;
    RetrofitApiCall retrofitApiCall;
    Bitmap frontImage, backImage;
    CompositeDisposable disposable = new CompositeDisposable();
    MaterialCardView progressCardView;
    File file1, file2;
    Uri uri1, uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nidimage_confirm);

        review_confirm_button = findViewById(R.id.review_cnfm_btnId);
        imageViewId_nidFront = findViewById(R.id.imageViewId_nidFront);
        imageViewId_nidback = findViewById(R.id.imageViewId_nidBack);
        backNidReview_ButtonId = findViewById(R.id.backNidReview_ButtonId);
        progressCardView = findViewById(R.id.progressCardView);

        file1 = new File(SharedPreferenceClass.
                getVal(getApplicationContext(), "frontImage"));

        file2 = new File(SharedPreferenceClass.
                getVal(getApplicationContext(), "backImage"));

        uri1 = Uri.fromFile(file1);

        uri2 = Uri.fromFile(file2);


        try {
            frontImage = ImageUtils.handleSamplingAndRotationBitmap(getApplicationContext(), uri1);

            imageViewId_nidFront.setImageBitmap(frontImage);

            backImage = ImageUtils.handleSamplingAndRotationBitmap(getApplicationContext(), uri2);

            imageViewId_nidback.setImageBitmap(backImage);
        } catch (IOException e) {
            e.printStackTrace();
        }




        review_confirm_button.setOnClickListener(v -> uploadImageToServer());

        backNidReview_ButtonId.setOnClickListener(v -> finish());

        //uploadImageToServer();

    }

    void uploadImageToServer() {

        List<Uri> uris = new ArrayList<>();

//        Uri uri1 = getImageUri(getApplicationContext(), frontImage);
//        Log.v("URI1", "" + getImageUri(getApplicationContext(), frontImage));
//
//        Uri uri2 = getImageUri(getApplicationContext(), backImage);
//        Log.v("URI2", "" + getImageUri(getApplicationContext(), backImage));

        uris.add(0, uri1);
        uris.add(0, uri2);

        uploadImages(uris);

    }

    void uploadImages(List<Uri> fileUris) {

        retrofitApiCall = RetroFitInstance.retrofitInstance().create(RetrofitApiCall.class);

        List<MultipartBody.Part> images = new ArrayList<>();

//        for (int i=0;i<=fileUris.size();i++){

        images.add(prepareImage("id_front", fileUris.get(0)));
        images.add(prepareImage("id_back", fileUris.get(1)));

//        }

        Map<String, String> map = new HashMap<>();

        map.put("step", "1");
        map.put("crop", "1");

        progressCardView.setVisibility(View.VISIBLE);

        review_confirm_button.setEnabled(false);
        review_confirm_button.setTextColor(Color.parseColor("#9B9B9B"));

        disposable.add(retrofitApiCall.imageUpload("Token " + SharedPreferenceClass.getVal(getApplicationContext(), "agentToken"), map,
                new HashMap<>(), images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<NidResponse>() {

                    @Override
                    public void onSuccess(NidResponse nidResponse) {
                        progressCardView.setVisibility(View.GONE);
                        review_confirm_button.setEnabled(true);
                        review_confirm_button.setTextColor(Color.parseColor("#f9f9f9"));
                        if (nidResponse.getStatus().equals("success")) {
                            Intent intent = new Intent(new Intent(getApplicationContext(), ReviewInformationActivity.class));
                            intent.putExtra("NidResponse", nidResponse);
                            startActivity(intent);
                        } else {
                            Toast.makeText(NIDImageConfirm.this, nidResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressCardView.setVisibility(View.GONE);
                        review_confirm_button.setEnabled(true);
                        review_confirm_button.setTextColor(Color.parseColor("#f9f9f9"));
                        Toast.makeText(NIDImageConfirm.this, "Failed to send Nid card's images.", Toast.LENGTH_LONG).show();
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
