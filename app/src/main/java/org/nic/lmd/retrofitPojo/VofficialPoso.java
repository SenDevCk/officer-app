package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class VofficialPoso {
    @SerializedName("partnerId")
    public int partnerId;
    @SerializedName(value="vendorId",alternate = {"manufacturerId"})
    public String vendorId;
    @SerializedName("partnerName")
    public String partnerName;
    @SerializedName("fatherHusbandName")
    public String fatherHusbandName;
    @SerializedName("aadhaarNo")
    public String aadhaarNo;
    @SerializedName("address1")
    public String address1;
    @SerializedName("address2")
    public String address2;
    @SerializedName("state")
    public int state;
    @SerializedName("city")
    public String city;
    @SerializedName("district")
    public int district;
    @SerializedName("block")
    public int block;
    @SerializedName("landmark")
    public String landmark;
    @SerializedName("pincode")
    public String pincode;
    @SerializedName("mobileNo")
    public String mobileNo;
    @SerializedName("landlineNo")
    public String landlineNo;
    @SerializedName("emailId")
    public String emailId;
    @SerializedName("nominatedUnderSection")
    public boolean nominatedUnderSection;
    @SerializedName("designation")
    public String designation;
}
