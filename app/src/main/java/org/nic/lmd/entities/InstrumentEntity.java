package org.nic.lmd.entities;

import java.util.ArrayList;
import lombok.Data;

@Data
public class InstrumentEntity {

    private String id;
    private String vendorId;
    private String vcId;
    private String pro_id;
    private String cat_id;
    private String cap_id;
    private String quantity;
    private String ins_class;
    private String m_or_brand;
    private String val_year;
    private String cap_max;
    private String cap_min;
    private String model_no;
    private String ser_no;
    private String e_val;
    private ArrayList<Nozzle> nozzles;
}
