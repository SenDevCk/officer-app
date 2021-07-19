package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaInspection {
	@SerializedName("trader_id")
	private Long traderId;
	@SerializedName("vc_no")
	private Integer vcNo;
	@SerializedName("business_type")
	private Integer businessType;
	@SerializedName("premises_name")
	private String premisesName;
	@SerializedName("address")
	private String address;
	@SerializedName("owner_name")
	private String ownerName;
	@SerializedName("date_inspection")
	private Date dateInspection;
	@SerializedName("is_present")
	private boolean isPresent;
	@SerializedName("name")
	private String name;
	@SerializedName("father_name")
	private String fatherName;
	@SerializedName("is_displayed")
	private boolean isDisplayed;
	@SerializedName("vc_date")
	private Date vcDate;
	@SerializedName("vc_valididity")
	private Date vcValididity;
	@SerializedName("premises_image_path")
	private String premisesImagePath;
	@SerializedName("documents")
	private Set<Document> documents;
}
