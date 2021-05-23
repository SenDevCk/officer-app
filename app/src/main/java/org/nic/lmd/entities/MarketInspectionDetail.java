package org.nic.lmd.entities;


import com.google.gson.annotations.SerializedName;
public class MarketInspectionDetail {
    @SerializedName("mmid")
    public  long mmid;
	@SerializedName("m_month")
    public  int m_month;
	@SerializedName("m_year")
    public  int m_year;
	@SerializedName("nature_of_business")
    public  NatureOfBusiness nature_of_business;
	@SerializedName("current_count")
    public  long current_count;
	@SerializedName("previous_accu_count")
    public  long previous_accu_count;
	@SerializedName("sub_div_id")
    public  SubDivision sub_div;
	@SerializedName("mar_ins_type")
    public  MarketInspectionTab mar_ins_type;
	@SerializedName("user_id")
    public  String user_id;


}
