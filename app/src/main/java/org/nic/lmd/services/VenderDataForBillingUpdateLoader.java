package org.nic.lmd.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;
import org.nic.lmd.interfaces.VendorDataListener;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;


public class VenderDataForBillingUpdateLoader extends AsyncTask<String, Void, String> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    JSONObject jsonObject;
    private static VendorDataListener vendorDataListener;


    public VenderDataForBillingUpdateLoader(Activity activity, JSONObject jsonObject) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onPreExecute() {
        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("Wait updating...");
        this.dialog1.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String json_res = null;
        try {
                json_res = WebHandler.callByPost(jsonObject.toString(), Urls_this_pro.LOAD_VENDOR_BILLING_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
            return json_res;
        }
        return json_res;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        if (dialog1.isShowing()) dialog1.dismiss();
        if (res != null) {
            try {
                JSONObject jsonObject_res=new JSONObject(res);
                if (jsonObject_res.getInt("statusCode")==1){
                    vendorDataListener.responseFound(jsonObject.getString("status").trim());
                }else{
                    vendorDataListener.responseNotFound(jsonObject.getString("status").trim());
                }

            } catch (Exception e) {
                e.printStackTrace();
                vendorDataListener.responseNotFound(e.getMessage());
            }
        } else {
            //Toast.makeText(activity, "Server Problem Or Somthing went wrong !", Toast.LENGTH_SHORT).show();
            Log.e("log", "null on vender date fee updating");
            vendorDataListener.responseNotFound("Server Problem !");
        }
    }

    public static void listenVendorUpdate(VendorDataListener vendorDataListener1) {
        vendorDataListener = vendorDataListener1;
    }
}

