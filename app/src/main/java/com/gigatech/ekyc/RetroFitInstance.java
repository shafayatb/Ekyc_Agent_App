package com.gigatech.ekyc;

import retrofit2.Retrofit;

public class RetroFitInstance {

    private static Retrofit retrofit;
    static String BASE_URL = "http://puthi-ocr.com:3000/app/";

    public static Retrofit retrofitInstance(){

        if ( retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();

        }

        return retrofit;

    }

}
