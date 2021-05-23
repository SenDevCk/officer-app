package org.nic.lmd.entities;
import lombok.Data;

@Data
public class InsCapacityEntity {
    private String capacityId;
    private String categoryId;
    private String capacityValue;
    private String capacityDesc;
    private String capacityOrder;
    private String checked;
}
