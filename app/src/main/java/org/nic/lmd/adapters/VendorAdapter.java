package org.nic.lmd.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.officerapp.VerificationFeeCalculationActivity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.officerapp.VerificationLMOActivity;
import org.nic.lmd.retrofitPojo.VendorPoso;

import java.util.List;

public class VendorAdapter extends BaseAdapter {
    Activity activity;
    List<VendorPoso> res;
    LayoutInflater layoutInflater;
    String which;

    public VendorAdapter(Activity activity, List<VendorPoso> res,String which) {
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
        final VendorPoso vendor=res.get(position);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.text_ven_id = rootview.findViewById(R.id.text_ven_id);
        viewHolder.text_ven_id.setText(""+vendor.vendorId);
        viewHolder.text_soap = rootview.findViewById(R.id.text_soap);
        viewHolder.text_soap.setText(""+vendor.nameOfBusinessShop);
        viewHolder.text_pre_type = rootview.findViewById(R.id.text_pre_type);
        viewHolder.text_pre_type.setText("" + new DataBaseHelper(activity).getPremissesByID(vendor.premisesType).getName());
        viewHolder.text_mob = rootview.findViewById(R.id.text_mob);
        viewHolder.text_mob.setText("" +vendor.mobileNo);

        rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=null;
                if (which.equals("pending"))
                intent = new Intent(activity, VerificationFeeCalculationActivity.class);
                else if (which.equals("verified"))
                intent = new Intent(activity, VerificationLMOActivity.class);
                intent.putExtra("vid", vendor.vendorId);
                activity.startActivity(intent);
            }
        });

        return rootview;
    }

    class ViewHolder {
        TextView text_ven_id, text_soap, text_pre_type, text_mob;
    }
}
