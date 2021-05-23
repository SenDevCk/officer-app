package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class WeightPoso {
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("vcId")
    public int vcId;
    @SerializedName("denomination")
    public int denomination;
    @SerializedName("proposalId")
    public int proposalId;
    @SerializedName("categoryId")
    public int categoryId;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("validYear")
    public int validYear;
    @SerializedName("vfRate")
    public double vfRate;
    @SerializedName("vfAmount")
    public double vfAmount;
    @SerializedName("afAmount")
    public double afAmount;
    @SerializedName("urAmount")
    public double urAmount;
    @SerializedName("nextverificationQtr")
    public String nextverificationQtr;
    @SerializedName("duesQtr")
    public double duesQtr;
    @SerializedName("status")
    public String status="N";
    @SerializedName("denominationValue")
    public String denominationValue;
    @SerializedName("categoryName")
    public String categoryName;
    @SerializedName("nextverificationQtrName")
    public String nextverificationQtrName;
    @SerializedName("currentQtr")
    public String currentQtr;
    @SerializedName("lastVerificationQtr")
    public String lastVerificationQtr;
}
