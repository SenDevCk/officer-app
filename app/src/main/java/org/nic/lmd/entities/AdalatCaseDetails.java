package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class AdalatCaseDetails implements Serializable {
	@SerializedName("sl_no")
	private Long sl_no;
	@SerializedName("case_number")
	private String case_number;
	@SerializedName("compounding_amt")
	private Double compounding_amt;
	@SerializedName("adalatPay")
	private AdalatPayDetails adalatPay;
	@SerializedName("consumers")
	private ArrayList<AdalatConsumerDetail> consumers;
}
