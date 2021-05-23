package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class ManufacturerDataResponse {
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("status")
    public String status;
    @SerializedName("remarks")
    public String remarks;
    @SerializedName("size")
    public int size;
    @SerializedName("data")
    public ManufacturerPoso manufacturerPoso;
}
