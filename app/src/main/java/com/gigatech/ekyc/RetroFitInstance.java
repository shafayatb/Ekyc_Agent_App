package com.gigatech.ekyc;

import retrofit2.Retrofit;

public class RetroFitInstance {

    private static Retrofit retrofit;
    static String BASE_URL = "https://3d81a352-b8da-4670-a901-3d1b4540272f.mock.pstmn.io/";

    public static Retrofit retrofitInstance(){

        if ( retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();

        }

        return retrofit;

    }

}
