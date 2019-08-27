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
    @SerializedName("id_front_name")
    @Expose
    private String idFrontUrl;
    @SerializedName("id_back_name")
    @Expose
    private String idBackUrl;

    @SerializedName("perm_address")
    @Expose
    private String permAddress;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("nominee")
    @Expose
    private String nominee;
    @SerializedName("nominee_relation")
    @Expose
    private String nomineeRelation;
    @SerializedName("id_front_image")
    @Expose
    private String idFrontImage;
    @SerializedName("id_back_image")
    @Expose
    private String idBackImage;

    public String getIdFrontImage() {
        return idFrontImage;
    }

    public void setIdFrontImage(String idFrontImage) {
        this.idFrontImage = idFrontImage;
    }

    public String getIdBackImage() {
        return idBackImage;
    }

    public void setIdBackImage(String idBackImage) {
        this.idBackImage = idBackImage;
    }

    public String getPermAddress() {
        return permAddress;
    }

    public void setPermAddress(String permAddress) {
        this.permAddress = permAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

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

    public NidData() {
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
        dest.writeString(this.permAddress);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.gender);
        dest.writeString(this.profession);
        dest.writeString(this.nominee);
        dest.writeString(this.nomineeRelation);
        dest.writeString(this.idFrontImage);
        dest.writeString(this.idBackImage);
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
        this.permAddress = in.readString();
        this.mobileNumber = in.readString();
        this.gender = in.readString();
        this.profession = in.readString();
        this.nominee = in.readString();
        this.nomineeRelation = in.readString();
        this.idFrontImage = in.readString();
        this.idBackImage = in.readString();
    }

    public static final Creator<NidData> CREATOR = new Creator<NidData>() {
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
