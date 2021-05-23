package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class WeightManufacturePojo {
    @SerializedName("weightId")
    public int weightId;
    @SerializedName("category")
    public int category;
    @SerializedName("denomination")
    public int denomination;
    @SerializedName("proposal")
    public int proposal;
    @SerializedName("manufacturerId")
    public long manufacturerId;
}
