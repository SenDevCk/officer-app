package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class VofficialManufacturePojo {
    @SerializedName("officialId")
    public int officialId;
    @SerializedName("manufacturerId")
    public long manufacturerId;
    @SerializedName("designation")
    public int designation;
    @SerializedName("name")
    public String name;
    @SerializedName("father")
    public String father;
    @SerializedName("aadharNo")
    public String aadharNo;
    @SerializedName("idNo")
    public String idNo;
    @SerializedName("address1")
    public String address1;
    @SerializedName("address2")
    public String address2;
    @SerializedName("country")
    public String country;
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
    public long pincode;
    @SerializedName("mobile")
    public long mobile;
    @SerializedName("landline")
    public long landline;
    @SerializedName("email")
    public String email;
    @SerializedName("nominated")
    public boolean nominated;
}
