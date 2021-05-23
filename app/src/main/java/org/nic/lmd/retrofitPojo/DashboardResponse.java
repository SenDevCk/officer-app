package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DashboardResponse {
    @SerializedName("statusCode")
    public int statusCode;
    @SerializedName("status")
    public String status;
    @SerializedName("remarks")
    public String remarks;
    @SerializedName("size")
    public long size;
    @SerializedName("data")
    public Data data;
    public class Data{
        @SerializedName("vendor")
        public ArrayList<VendorPoso> vendors=new ArrayList<>();
        @SerializedName("manufacturer")
        public ArrayList<ManufacturerPoso> manufacturers=new ArrayList<>();
    }
}
