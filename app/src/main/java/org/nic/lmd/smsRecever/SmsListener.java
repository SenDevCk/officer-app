package org.nic.lmd.smsRecever;

/**
 * Created by NIC2 on 1/6/2018.
 */

public interface SmsListener {
    public void messageReceived(String messageText);
}

