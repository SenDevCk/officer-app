package org.nic.lmd.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Set;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AdalatPayDetails implements Serializable {
	private Long sl_no;
	private String payment_mode;
	private String grn_number;
	private Double amount;
	private Double amount_adjust;
	private String type_of_adalat;
	private String date_of_adalat;
	private String name_of_court;
	private String user_id;
	private Integer sub_div_id;
	@SerializedName("cases")
	private ArrayList<AdalatCaseDetails> cases;
}
