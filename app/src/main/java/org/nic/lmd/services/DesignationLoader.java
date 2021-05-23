package org.nic.lmd.services;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.Designation;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;

import java.util.ArrayList;

public class DesignationLoader extends AsyncTask<String,Void, ArrayList<Designation>> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;

    public DesignationLoader(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {

        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("DESIGNATION LOADING...");
        this.dialog1.show();
    }

    @Override
    protected ArrayList<Designation> doInBackground(String... strings) {
        String json_res= WebHandler.callByGet(Urls_this_pro.LOAD_DESIGNATION);

        return WebServiceHelper.designationParser(json_res);
    }

    @Override
    protected void onPostExecute(ArrayList<Designation> res) {
        super.onPostExecute(res);
        if (res!=null){
            if (res.size()>0){
                long c=new DataBaseHelper(activity).saveDesignation(res);
                Log.d("Designations","Total= "+c);
                if (dialog1.isShowing())dialog1.dismiss();
                if (c>0){
                    new PremisesLoader(activity).execute();
                }else {
                    Toast.makeText(activity, "Degignation not saved !", Toast.LENGTH_SHORT).show();
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

