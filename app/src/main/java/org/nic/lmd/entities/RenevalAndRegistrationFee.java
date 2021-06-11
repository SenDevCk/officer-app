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
	@SerializedName("mcurrent")
	private double mCurrent;
	@SerializedName("mtotalCurrent")
	private double mTotalCurrent;
	@SerializedName("dcurrent")
	private double dCurrent;
	@SerializedName("dtotalCurrent")
	private double dTotalCurrent;
	@SerializedName("rcurrent")
	private double rCurrent;
	@SerializedName("rtotalCurrent")
	private double rTotalCurrent;

	@SerializedName("pcurrent")
	private double pCurrent;
	@SerializedName("ptotalCurrent")
	private double pTotalCurrent;
	
}
