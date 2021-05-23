package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class NatureOfBusiness {
    @SerializedName("bn_id")
    private  String id;
    @SerializedName("bn_name")
    private   String value;
}
