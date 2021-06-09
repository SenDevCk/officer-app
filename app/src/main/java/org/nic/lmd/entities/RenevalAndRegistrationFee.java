package org.nic.lmd.entities;


import com.google.gson.annotations.SerializedName;

import lombok.Data;


@Data
public class RenevalAndRegistrationFee {

	@SerializedName("renRegId")
	private long renRegId;
	@SerializedName("month")
	private int month;
	@SerializedName("year")
	private int year;
	@SerializedName("user_id")
	private String user_id;
	@SerializedName("sub_div")
	private SubDivision sub_div;
	@SerializedName("mCurrent")
	private double mCurrent;
	@SerializedName("mTotalCurrent")
	private double mTotalCurrent;
	@SerializedName("dCurrent")
	private double dCurrent;
	@SerializedName("dTotalCurrent")
	private double dTotalCurrent;
	@SerializedName("rCurrent")
	private double rCurrent;
	@SerializedName("rTotalCurrent")
	private double rTotalCurrent;

	@SerializedName("pCurrent")
	private double pCurrent;
	@SerializedName("pTotalCurrent")
	private double pTotalCurrent;
	
}
