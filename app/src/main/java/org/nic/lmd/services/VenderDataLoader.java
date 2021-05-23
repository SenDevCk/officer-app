package org.nic.lmd.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import org.nic.lmd.interfaces.VendorDataListener;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;


public class VenderDataLoader extends AsyncTask<String,Void, String> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    private static VendorDataListener vendorDataListener;
    public VenderDataLoader(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {
        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("Wait loading...");
        this.dialog1.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String json_res=null;
        try{
            json_res= WebHandler.callByGet(Urls_this_pro.LOAD_VENDOR+strings[0].trim());
        }catch (Exception e){
            e.printStackTrace();
            return json_res;
        }
        return json_res;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        if (dialog1.isShowing())dialog1.dismiss();
        if (res!=null){
            try {
                vendorDataListener.responseFound(res);
            }catch (Exception e){
                e.printStackTrace();
                vendorDataListener.responseNotFound("Exception Found !");
            }
        }else{
            //Toast.makeText(activity, "Server Problem Or Somthing went wrong !", Toast.LENGTH_SHORT).show();
            Log.e("log","null on Vender List Loader");
        }
    }

    public static void listenVendorList(VendorDataListener vendorDataListener1){
        vendorDataListener=vendorDataListener1;
    }
}

