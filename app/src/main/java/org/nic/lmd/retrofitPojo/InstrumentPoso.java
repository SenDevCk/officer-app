package org.nic.lmd.retrofitPojo;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InstrumentPoso {
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("slNo")
    public int slNo;
    @SerializedName("vcId")
    public int vcId;
    @SerializedName("capacityId")
    public int capacityId;
    @SerializedName("proposalId")
    public int proposalId;
    @SerializedName("categoryId")
    public int categoryId;
    @SerializedName("classId")
    public int classId;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("manufacturer")
    public String manufacturer;
    @SerializedName("capacityMax")
    public String capacityMax;
    @SerializedName("capacityMin")
    public String capacityMin;
    @SerializedName("validYear")
    public int validYear;
    @SerializedName("modelNo")
    public String modelNo;
    @SerializedName("mserialNo")
    public String mserialNo;
    @SerializedName("vfRate")
    public String vfRate;
    @SerializedName("vfAmount")
    public double vfAmount;
    @SerializedName("afAmount")
    public double afAmount;
    @SerializedName("urAmount")
    public double urAmount;
    @SerializedName("nextverificationQtr")
    public String nextverificationQtr;

    @SerializedName("duesQtr")
    public String duesQtr;
    @SerializedName("status")
    @Nullable
    @Expose
    public String status;
    @SerializedName("capacityValue")
    public String capacityValue;
    @SerializedName("categoryName")
    public String categoryName;
    @SerializedName("nextverificationQtrName")
    public String nextverificationQtrName;
    @SerializedName("currentQtr")
    public String currentQtr;
    @SerializedName("lastVerificationQtr")
    public String lastVerificationQtr;
    @SerializedName("extensions")
    public ArrayList<ExtentionPoso> extensions = new ArrayList<ExtentionPoso>();
    @SerializedName("evalue")
    public String evalue;
}
