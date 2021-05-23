package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class ModelManufacturerPojo {

    @SerializedName("approvedId")
    public int approvedId;
    @SerializedName("manufacturerId")
    public long manufacturerId;
    @SerializedName("model")
    public String model;
    @SerializedName("approvedDate")
    public String approvedDate;
    @SerializedName("description")
    public String description;
    @SerializedName("brand")
    public String brand;
    @SerializedName("series")
    public String series;
    @SerializedName("approved")
    public boolean approved;
}
