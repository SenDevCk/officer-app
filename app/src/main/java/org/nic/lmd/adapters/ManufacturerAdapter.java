package org.nic.lmd.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.officerapp.ManufactureFeeCalculationActivity;
import org.nic.lmd.officerapp.VerificationFeeCalculationActivity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.retrofitPojo.ManufacturerPoso;

import java.util.List;

public class ManufacturerAdapter extends BaseAdapter {
    Activity activity;
    List<ManufacturerPoso> res;
    LayoutInflater layoutInflater;
    String which;
    public ManufacturerAdapter(Activity activity, List<ManufacturerPoso> res,String which) {
        this.activity = activity;
        layoutInflater = this.activity.getLayoutInflater();
        this.res = res;
        this.which = which;
    }

    @Override
    public int getCount() {
        return res.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View rootview, ViewGroup viewGroup) {
        rootview = layoutInflater.inflate(R.layout.application_list_item, null, false);
        ViewHolder viewHolder = new ViewHolder();
            ManufacturerPoso manufacturerPoso=res.get(position);
            viewHolder.tag_text =  rootview.findViewById(R.id.tag_text);
            viewHolder.tag_text.setText(" (Manufacture ID)");
            viewHolder.text_ven_id =  rootview.findViewById(R.id.text_ven_id);
            viewHolder.text_ven_id.setText(""+manufacturerPoso.manufacturerId);
            viewHolder.text_soap =  rootview.findViewById(R.id.text_soap);
            viewHolder.text_soap.setText("" +manufacturerPoso.name);
            viewHolder.text_pre_type =  rootview.findViewById(R.id.text_pre_type);
            viewHolder.text_pre_type.setText("" + new DataBaseHelper(activity).getPremissesByID(String.valueOf(manufacturerPoso.premisesType)).getName());
            viewHolder.text_mob =  rootview.findViewById(R.id.text_mob);
            viewHolder.text_mob.setText("" + manufacturerPoso.mobile);
            rootview.setOnClickListener(view -> {
                    Intent intent = new Intent(activity, ManufactureFeeCalculationActivity.class);
                    intent.putExtra("man_id",manufacturerPoso.manufacturerId);
                    activity.startActivity(intent);
            });
        return rootview;
    }

    class ViewHolder {
        TextView text_ven_id, text_soap, text_pre_type, text_mob,tag_text;
    }
}
