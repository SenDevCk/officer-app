package org.nic.lmd.interfaces;

import org.json.JSONArray;

import java.util.ArrayList;

public interface DataListener {

    void dataFound(JSONArray data,int flag_for_data_type);
    void dataNotFound(String msg);
}
