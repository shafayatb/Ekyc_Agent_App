package com.gigatech.ekyc.remote;

public final class ApiEndpoints {

    private ApiEndpoints(){

    }

    private static String MIDDLE_URL = "app/";

    static String REQUEST_OTP_URL = MIDDLE_URL + "request-otp/";

    static String VERIFY_OTP_URL = MIDDLE_URL + "verify-otp/";

}
