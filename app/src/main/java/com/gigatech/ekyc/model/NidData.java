package com.gigatech.ekyc.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NidData implements Parcelable {

    @SerializedName("nid_no")
    @Expose
    private String nidNo;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("customer_name_ben")
    @Expose
    private String customerNameBen;
    @SerializedName("customer_name_eng")
    @Expose
    private String customerNameEng;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("id_front_url")
    @Expose
    private String idFrontUrl;
    @SerializedName("id_back_url")
    @Expose
    private String idBackUrl;

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCustomerNameBen() {
        return customerNameBen;
    }

    public void setCustomerNameBen(String customerNameBen) {
        this.customerNameBen = customerNameBen;
    }

    public String getCustomerNameEng() {
        return customerNameEng;
    }

    public void setCustomerNameEng(String customerNameEng) {
        this.customerNameEng = customerNameEng;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdFrontUrl() {
        return idFrontUrl;
    }

    public void setIdFrontUrl(String idFrontUrl) {
        this.idFrontUrl = idFrontUrl;
    }

    public String getIdBackUrl() {
        return idBackUrl;
    }

    public void setIdBackUrl(String idBackUrl) {
        this.idBackUrl = idBackUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nidNo);
        dest.writeString(this.dob);
        dest.writeString(this.customerNameBen);
        dest.writeString(this.customerNameEng);
        dest.writeString(this.fatherName);
        dest.writeString(this.motherName);
        dest.writeString(this.address);
        dest.writeString(this.idFrontUrl);
        dest.writeString(this.idBackUrl);
    }

    public NidData() {
    }

    protected NidData(Parcel in) {
        this.nidNo = in.readString();
        this.dob = in.readString();
        this.customerNameBen = in.readString();
        this.customerNameEng = in.readString();
        this.fatherName = in.readString();
        this.motherName = in.readString();
        this.address = in.readString();
        this.idFrontUrl = in.readString();
        this.idBackUrl = in.readString();
    }

    public static final Parcelable.Creator<NidData> CREATOR = new Parcelable.Creator<NidData>() {
        @Override
        public NidData createFromParcel(Parcel source) {
            return new NidData(source);
        }

        @Override
        public NidData[] newArray(int size) {
            return new NidData[size];
        }
    };
}
