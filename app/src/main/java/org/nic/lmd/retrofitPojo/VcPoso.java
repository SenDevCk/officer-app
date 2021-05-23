package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class VcPoso {
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("vcId")
    public int vcId;
    @SerializedName("vcDate")
    public String vcDate;
    @SerializedName("vcNumber")
    public String vcNumber;
    @SerializedName("nextVcDate")
    public String nextVcDate;
}
