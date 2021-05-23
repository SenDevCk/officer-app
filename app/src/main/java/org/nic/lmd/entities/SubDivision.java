package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SubDivision {
    @SerializedName("sub_div_id")
    private int id;
    @SerializedName("dist_id")
    private int dist_id;
    @SerializedName("sub_div_name")
    private String sub_div_name;
    @SerializedName("sub_div_name_hin")
    private String name_hn;
    @SerializedName("office_code")
    private String officeCode;
}
