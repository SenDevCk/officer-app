/**
 * 
 */
package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author sudhakar
 *
 */
@AllArgsConstructor
@Builder
@Data
public  class MyResponse<T> {
	@SerializedName("statusCode")
	private Integer statusCode;
	@SerializedName("status")
	private String status;
	@SerializedName("remarks")
	private String remarks;
	@SerializedName("size")
	private int size;
	@SerializedName("data")
	private T data;
}