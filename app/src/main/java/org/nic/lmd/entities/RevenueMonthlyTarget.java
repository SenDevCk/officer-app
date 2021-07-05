package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RevenueMonthlyTarget {
    @SerializedName("tar_id")
    private long tar_id;
    @SerializedName("vf_target")
    private double vf_target;
    @SerializedName("af_target")
    private double af_target;
    @SerializedName("cf_target")
    private double cf_target;
    @SerializedName("lic_ren_fee")
    private double lic_ren_fee;
    @SerializedName("reg_fee")
    private double reg_fee;
    @SerializedName("tyear")
    private int tYear;
    @SerializedName("tmonth")
    private int tMonth;
    @SerializedName("subDiv")
    private int subDiv;
}
