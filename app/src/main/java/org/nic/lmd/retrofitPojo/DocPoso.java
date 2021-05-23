package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

public class DocPoso {
    @SerializedName("documentId")
    public int documentId;
    @SerializedName("vendorId")
    public String vendorId;
    @SerializedName("docUrl")
    public String docUrl;
    @SerializedName("docNo")
    public int docNo;
    @SerializedName("available")
    public boolean available;
}
