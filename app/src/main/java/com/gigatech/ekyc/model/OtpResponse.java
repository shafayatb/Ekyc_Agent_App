package com.gigatech.ekyc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("status")
    @Expose
    private String status = "";

    @SerializedName("token")
    @Expose
    private String token = "";

    @SerializedName("detail")
    @Expose
    private String detail = "";

    @SerializedName("error")
    @Expose
    private String error = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
