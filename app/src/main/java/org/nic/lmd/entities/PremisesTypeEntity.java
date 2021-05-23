package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class PremisesTypeEntity {
    @SerializedName("bn_id")
    private String value;
    @SerializedName("bn_name")
    private String name;
}
