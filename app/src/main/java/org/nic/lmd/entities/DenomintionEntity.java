package org.nic.lmd.entities;
import lombok.Data;

@Data
public class DenomintionEntity {
    private String value;
    private String vcId;
    private String vendorId;
    private String categoryId;
    private String name;
    private String denominationDesc;
    private String denominationOrder;
    private String remarks;
    private String checked; //(N/Y)
    private String quantity; //no of quantity
    private String set_m;//no of sets
    private String fee;
    private String val_year;
    private String pro_id;
    private String is_set;//(N/Y)
}
