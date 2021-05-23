package org.nic.lmd.entities;
import lombok.Data;

@Data
public class Nozzle {
    private int id;
    private String sl_id;
    private String nozzle_num;
    private String product_id;
    private String cal_num;
    private String k_factor;
    private String tot_value;
    private String vendorId;
    private String vcId;
}
