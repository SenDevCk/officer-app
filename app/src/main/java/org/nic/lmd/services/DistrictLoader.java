package org.nic.lmd.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.District;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;

import java.util.ArrayList;

public class DistrictLoader extends AsyncTask<String, Void, ArrayList<District>> {

    Activity activity;
    private ProgressDialog dialog1;

    public DistrictLoader(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
    }

    @Override
    protected void onPreExecute() {

        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("DISTRICT LOADING...");
        this.dialog1.show();
    }

    @Override
    protected ArrayList<District> doInBackground(String... strings) {
        String json_res= WebHandler.callByGet(Urls_this_pro.LOAD_DISTRICT);

        return WebServiceHelper.districtParser(json_res);
    }

    @Override
    protected void onPostExecute(ArrayList<District> res) {
        super.onPostExecute(res);
        if (res!=null){
            if (res.size()>0){
                long c=new DataBaseHelper(activity).saveDistrict(res);
                Log.d("DIstrict","Total= "+c);
                if (dialog1.isShowing())dialog1.dismiss();
                if (c>0){
                    new BlockLoader(activity).execute();
                }else {
                    Toast.makeText(activity, "District not saved !", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(activity, "No data found !", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Toast.makeText(activity, "Something went wrong !", Toast.LENGTH_SHORT).show();
            Log.e("log","null on Designation");
        }
    }
}

