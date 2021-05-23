package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class InstrumentManufacturePojo {
    @SerializedName("instrumentId")
    public int instrumentId;
    @SerializedName("proposal")
    public int proposal;
    @SerializedName("capacity")
    public int capacity;
    @SerializedName("insClass")
    public int insClass;
    @SerializedName("manufacturerId")
    public long manufacturerId;
    @SerializedName("category")
    public int category;
    @SerializedName("maxCapacity")
    public String maxCapacity;
    @SerializedName("minCapacity")
    public String minCapacity;
    @SerializedName("model")
    public String model;
    @SerializedName("serial")
    public String serial;
    @SerializedName("evalue")
    public String evalue;
    @SerializedName("kfactor")
    public String kfactor;
    @SerializedName("totalizer")
    public String totalizer;
}
