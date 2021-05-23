package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class RevenueReportEntity {
        @SerializedName("rev_rep_id")
        private long rev_rep_id;
        @SerializedName("month")
        private int month;
        @SerializedName("year")
        private int year;
        @SerializedName("type_of_bussiness")
        private NatureOfBusiness type_of_bussiness;
        @SerializedName("user_id")
        private String user_id;
        @SerializedName("sub_div")
        private SubDivision sub_div;
        @SerializedName("vf_current")
        private double vf_current;
        @SerializedName("vf_total_current")
        private double vf_total_current;
        @SerializedName("af_current")
        private double af_current;
        @SerializedName("af_total_current")
        private double af_total_current;
        @SerializedName("cf_current")
        private double cf_current;
        @SerializedName("cf_total_current")
        private double cf_total_current;
}
