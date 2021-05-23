package org.nic.lmd.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nic.lmd.interfaces.DataListener;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;

import java.util.ArrayList;

public class LocationLoader extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    Activity activity;
    private static DataListener dataListener;
    private int i;

    public LocationLoader(Activity activity, int i) {
        this.activity = activity;
        this.i = i;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        if (i == 1) {
            progressDialog.setMessage("Loading Location");
        } else {
            progressDialog.setMessage("Loading Data");
        }
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String res = null;
        if (i == 1) {
            res = WebHandler.callByGet(Urls_this_pro.LOAD_LOCATION);
        } else {
            res = WebHandler.callByGet(Urls_this_pro.LOAD_AREA + strings[0] + "/" + strings[1]);
            Log.d("url", Urls_this_pro.LOAD_AREA + strings[0] + "/" + strings[1]);
        }
        return res;
    }

    @Override
    protected void onPostExecute(String datams) {
        super.onPostExecute(datams);
        if (progressDialog.isShowing()) progressDialog.dismiss();
        if (datams != null) {
            if (WebHandler.hasError(datams)) {
                Toast.makeText(activity, "" + WebHandler.getError(datams), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (i == 1) {
                        dataListener.dataFound(new JSONArray(datams), 1);
                    } else {
                        JSONObject jsonObject = new JSONObject(datams);
                        if (jsonObject.getJSONArray("data").length() > 0)
                            dataListener.dataFound(jsonObject.getJSONArray("data"), 2);
                        else dataListener.dataNotFound("No data found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dataListener.dataNotFound(e.getMessage());
                }
            }
        } else {
            dataListener.dataNotFound("Server Problem or Somthing went wrong !");
        }
    }


    public static void listenForData(DataListener dataListener1) {
        dataListener = dataListener1;
    }
}
