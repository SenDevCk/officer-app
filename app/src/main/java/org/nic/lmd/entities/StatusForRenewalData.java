package org.nic.lmd.entities;
import lombok.Data;

@Data
public class StatusForRenewalData {

    private boolean isDeleted;
    private boolean isRenewed;
    private Character isRepairable;
}
