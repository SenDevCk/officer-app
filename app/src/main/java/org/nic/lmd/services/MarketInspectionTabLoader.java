package org.nic.lmd.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.officerapp.LoginActivity;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;

import java.util.ArrayList;

public class MarketInspectionTabLoader extends AsyncTask<String, Void, ArrayList<MarketInspectionTab>> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;

    public MarketInspectionTabLoader(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {

        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("LOADING TABS...");
        this.dialog1.show();
    }

    @Override
    protected ArrayList<MarketInspectionTab> doInBackground(String... strings) {
        String json_res= WebHandler.callByGet(Urls_this_pro.GET_TAB_DETAILS);
        return WebServiceHelper.marketInspectionTabsParser(json_res);
    }

    @Override
    protected void onPostExecute(ArrayList<MarketInspectionTab> res) {
        super.onPostExecute(res);
        if (res!=null){
            if (res.size()>0){
                long c=new DataBaseHelper(activity).saveMarketInspectionTabs(res);
                Log.d("MarketInspectionTab","Total= "+c);
                if (dialog1.isShowing())dialog1.dismiss();
                if (c>0) {
                    CommonPref.setCheckUpdate(activity,true);
                    if (CommonPref.getCheckUpdate(activity)) {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                        activity.finish();
                    } else {
                        Toast.makeText(activity, "Something went Wrong !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.e("error","data not saved in market ins tabs");
                }
            }else {
                Toast.makeText(activity, "No data found !", Toast.LENGTH_SHORT).show();
            }
        }else{
            Log.e("log","null on MarketInspectionTab");
        }
    }
}

