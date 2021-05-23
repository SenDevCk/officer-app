package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


import lombok.Data;

@Data
public class MarketInspectionTab implements Serializable {
    @SerializedName("market_ins_id")
    private int market_ins_id;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    public String value;
}
