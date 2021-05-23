package org.nic.lmd.retrofitPojo;

import com.google.gson.annotations.SerializedName;

import org.nic.lmd.entities.RevenueMonthlyTarget;
import org.nic.lmd.entities.RevenueReportEntity;

import java.util.List;

import lombok.Data;

@Data
public class RequestForRevenueData {
    @SerializedName("revenueReportEntities_entry")
    List<RevenueReportEntity> revenueReportEntities_entry;
    @SerializedName("revenueMonthlyTarget")
    RevenueMonthlyTarget revenueMonthlyTarget;
}
