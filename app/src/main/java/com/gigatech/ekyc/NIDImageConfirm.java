package com.gigatech.ekyc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;
import com.gigatech.ekyc.utils.SharedPreferenceClass;

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
import okhttp3.ResponseBody;

public class NIDImageConfirm extends AppCompatActivity {

    Button review_confirm_button;
    ImageView imageViewId_nidFront, imageViewId_nidback;
    RetrofitApiCall retrofitApiCall;
    Bitmap frontImage, backImage;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nidimage_confirm);

        review_confirm_button = findViewById(R.id.review_cnfm_btnId);
        imageViewId_nidFront = findViewById(R.id.imageViewId_nidFront);
        imageViewId_nidback = findViewById(R.id.imageViewId_nidBack);


        frontImage = BitmapFactory.decodeFile(SharedPreferenceClass.
                getVal(getApplicationContext(), "frontImage"));

        imageViewId_nidFront.setImageBitmap(frontImage);

        backImage = BitmapFactory.decodeFile(SharedPreferenceClass.
                getVal(getApplicationContext(), "backImage"));

        imageViewId_nidback.setImageBitmap(backImage);


        review_confirm_button.setOnClickListener(v -> uploadImageToServer());

        //uploadImageToServer();

    }

    void uploadImageToServer() {

        List<Uri> uris = new ArrayList<>();

        File file1 = new File(SharedPreferenceClass.
                getVal(getApplicationContext(), "frontImage"));

        File file2 = new File(SharedPreferenceClass.
                getVal(getApplicationContext(), "backImage"));

        Uri uri1 = Uri.fromFile(file1);

        Uri uri2 = Uri.fromFile(file2);

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


        disposable.add(retrofitApiCall.imageUpload("Token " + SharedPreferenceClass.getVal(getApplicationContext(), "agentToken"), map, images)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<ResponseBody>() {

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            Log.v("Response", responseBody.string());
                            startActivity(new Intent(getApplicationContext(), ReviewInformationActivity.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("ErrorResponse", e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("ErrorResponse", e.getLocalizedMessage());
                    }
                }));

//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                Log.v("WRE",""+response.body());
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Log.v("WRE",""+call.toString());
//
//            }
//        });

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
