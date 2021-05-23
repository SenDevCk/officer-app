package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class EmployeeManufacturePojo {
     @SerializedName("manufacturerId")
     public long manufacturerId;
    @SerializedName("empType")
    public long empType;
    @SerializedName("count")
    public long count;
    @SerializedName("manufacturerEmpId")
    public long manufacturerEmpId;
}
