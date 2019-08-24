package com.gigatech.ekyc.remote;

import com.gigatech.ekyc.model.OtpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface RetrofitApiCall {

    @FormUrlEncoded
    @POST(ApiEndpoints.REQUEST_OTP_URL)
    Single<OtpResponse> getOtp(@Field("mobile_no") String mobileNo,
                               @Field("user_type") String userType);


    @FormUrlEncoded
    @POST(ApiEndpoints.VERIFY_OTP_URL)
    Single<OtpResponse> verifyOTP(@Field("mobile_no") String mobileNo,
                                  @Field("otp") String otp);

    @Multipart
    @POST(ApiEndpoints.CUSTOMER_REGISTRATION_URL)
    Single<ResponseBody> imageUpload(
//            @Part("description") RequestBody description,
            @Header("Authorization") String token,
            @QueryMap Map<String, String> stepsMap,
            @Part List<MultipartBody.Part> images);


}
