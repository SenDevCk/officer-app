package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class ExtentionPoso {
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("vcId")
    public int vcId;
    @SerializedName("insSlno")
    public int insSlno;
    @SerializedName("extSlno")
    public int extSlno;
    @SerializedName("nozalNo")
    public String nozalNo;
    @SerializedName("totalizerValue")
    public double totalizerValue;
    @SerializedName("product")
    public int product;
    @SerializedName("calNo")
    public double calNo;
    @SerializedName("kfactor")
    public double kfactor;
}
