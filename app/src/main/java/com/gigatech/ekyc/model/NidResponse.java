package com.gigatech.ekyc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NidResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("detail")
    @Expose
    private String detail;

    @SerializedName("data")
    @Expose
    private NidData data;

    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NidData getNidData() {
        return data;
    }

    public void setNidData(NidData data) {
        this.data = data;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message != null ? message : "Failed to upload Nid Images";
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NidResponse() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.detail);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.message);
    }

    protected NidResponse(Parcel in) {
        this.status = in.readString();
        this.detail = in.readString();
        this.data = in.readParcelable(NidData.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Creator<NidResponse> CREATOR = new Creator<NidResponse>() {
        @Override
        public NidResponse createFromParcel(Parcel source) {
            return new NidResponse(source);
        }

        @Override
        public NidResponse[] newArray(int size) {
            return new NidResponse[size];
        }
    };
}
