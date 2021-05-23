package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class VendorDataResponse {
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("status")
    public String status;
    @SerializedName("remarks")
    public String remarks;
    @SerializedName("size")
    public int size;
    @SerializedName("data")
    public VendorPoso vendorPoso;
}
