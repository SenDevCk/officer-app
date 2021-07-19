package org.nic.lmd.retrofitPojo;



import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Document {
	private class DocumentId {
		@SerializedName("document_id")
		private char documentId;
		@SerializedName("vender_id")
		private Long  venderId;
	}
	@SerializedName("path")
	private String path;
	@SerializedName("area_inspection")
	AreaInspection areaInspection;
	
}
