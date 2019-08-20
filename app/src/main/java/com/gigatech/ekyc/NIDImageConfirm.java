package com.gigatech.ekyc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gigatech.ekyc.remote.RetroFitInstance;
import com.gigatech.ekyc.remote.RetrofitApiCall;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NIDImageConfirm extends AppCompatActivity {

    Button review_confirm_button;
    ImageView imageViewId_nidFront,imageViewId_nidback;
    RetrofitApiCall retrofitApiCall;
    Bitmap frontImage, backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nidimage_confirm);

        review_confirm_button = findViewById(R.id.review_cnfm_btnId);
        imageViewId_nidFront = findViewById(R.id.imageViewId_nidFront);
        imageViewId_nidback = findViewById(R.id.imageViewId_nidBack);


        frontImage = BitmapFactory.decodeFile(SharedPreferenceClass.
                getVal(getApplicationContext(),"frontImage"));

        imageViewId_nidFront.setImageBitmap(frontImage);

        backImage = BitmapFactory.decodeFile(SharedPreferenceClass.
                getVal(getApplicationContext(),"backImage"));

        imageViewId_nidback.setImageBitmap(backImage);



        review_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ReviewInformationActivity.class));

            }
        });

        uploadImageToServer();

    }

    void uploadImageToServer(){

        List<Uri> uris = new ArrayList<>();

        Uri uri1= getImageUri(getApplicationContext(),frontImage);
        Log.v("URI1",""+getImageUri(getApplicationContext(),frontImage));

        Uri uri2= getImageUri(getApplicationContext(),backImage);
        Log.v("URI2",""+getImageUri(getApplicationContext(),backImage));

        uris.add(0,uri1);
        uris.add(0,uri2);

        uploadImages(uris);

    }

    void uploadImages(List<Uri> fileUris){

        retrofitApiCall = RetroFitInstance.retrofitInstance().create(RetrofitApiCall.class);

        List<MultipartBody.Part> images = new ArrayList<>();

//        for (int i=0;i<=fileUris.size();i++){

            images.add(prepareImage("frontImage",fileUris.get(0)));
            images.add(prepareImage("backImage",fileUris.get(1)));

//        }

        Call<ResponseBody> call = retrofitApiCall.imageNidUpload(images);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.v("WRE",""+response.body());

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.v("WRE",""+call.toString());

            }
        });

    }

    MultipartBody.Part prepareImage(String partName, Uri fileUri){
        File file = new File(getRealPathFromURI(fileUri));

        RequestBody requestBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))),file);

        return MultipartBody.Part.createFormData(partName,file.getName(),requestBody);
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
}
