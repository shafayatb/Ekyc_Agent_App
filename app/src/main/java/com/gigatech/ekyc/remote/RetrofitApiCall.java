package com.gigatech.ekyc.remote;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApiCall {

    @FormUrlEncoded
    @POST("mobileApi/checkLogin")
    Call<ResponseBody> getLoginDetails(@Field("username") String username,
                                       @Field("password") String password
                                       );


    @FormUrlEncoded
    @POST("mobileApi/checkLogin")
    Call<ResponseBody> getOTP(@Field("username") String username);

    @Multipart
    @POST("upload/nid-images")
    Call<ResponseBody> imageNidUpload(
//            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> images);


}
