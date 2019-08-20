package com.gigatech.ekyc.remote;

public final class ApiEndpoints {

    private ApiEndpoints() {

    }

    private static final String MIDDLE_URL = "app/";

    static final String REQUEST_OTP_URL = MIDDLE_URL + "request-otp/";

    static final String VERIFY_OTP_URL = MIDDLE_URL + "verify-otp/";

    static final String CUSTOMER_REGISTRATION_URL = "agent/customer-registration/";


}
