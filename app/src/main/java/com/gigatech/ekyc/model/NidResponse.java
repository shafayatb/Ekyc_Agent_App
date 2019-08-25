package com.gigatech.ekyc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NidResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private NidData data;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeParcelable(this.data, flags);
    }

    public NidResponse() {
    }

    protected NidResponse(Parcel in) {
        this.status = in.readString();
        this.data = in.readParcelable(NidData.class.getClassLoader());
    }

    public static final Parcelable.Creator<NidResponse> CREATOR = new Parcelable.Creator<NidResponse>() {
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
