package org.nic.lmd.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> trader = new ArrayList<String>();
        trader.add("Pending:0");
        trader.add("Varified:0");
        trader.add("Rejected:0");
        expandableListDetail.put("Trader Applications", trader);
        List<String> manufacturer = new ArrayList<String>();
        manufacturer.add("Pending:0");
        manufacturer.add("Varified:0");
        manufacturer.add("Rejected:0");
        expandableListDetail.put("Manufacturer Applications", manufacturer);
        List<String> scanner = new ArrayList<String>();
        scanner.add("Scanner");
        scanner.add("Market Inspection Details Entry");
        scanner.add("Revenue Details Entry");
        scanner.add("Reneval/Registration Entry");
        expandableListDetail.put("Utilities", scanner);
        List<String> settings = new ArrayList<String>();
        settings.add("Profile");
        settings.add("Logout");
        expandableListDetail.put("Settings", settings);
        return expandableListDetail;
    }
}