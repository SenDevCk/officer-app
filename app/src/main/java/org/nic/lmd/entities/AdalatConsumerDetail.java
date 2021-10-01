package org.nic.lmd.entities;




import com.google.gson.annotations.SerializedName;


import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdalatConsumerDetail implements Serializable {
	@SerializedName("sl_no")
	private Long sl_no;
	@SerializedName("district_id")
	private Integer district_id;
	@SerializedName("block_id")
	private Integer block_id;
	@SerializedName("type_of_consumer")
	private String type_of_consumer;
	@SerializedName("consumer_id")
	private String consumer_id;
	@SerializedName("consumer_address")
	private String consumer_address;
	@SerializedName("consumer_name")
	private String consumer_name;
	@SerializedName("caseDetail")
	private AdalatCaseDetails caseDetail;
}
