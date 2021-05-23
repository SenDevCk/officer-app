package org.nic.lmd.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.PatnerEntity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.retrofitPojo.VofficialManufacturePojo;

import java.util.ArrayList;

public class ManufacturerPatnerAdapter extends RecyclerView.Adapter<ManufacturerPatnerAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<VofficialManufacturePojo> patnerEntities;
    LayoutInflater layoutInflater;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_firm_type,text_lc_date,tv_reg_no,tv_desig,tv_name,tv_fname;
        public TextView tv_adhar,tv_add,landmark_tv,tv_city,tv_dis,tv_bol,tv_pin,tv_mb,tv_land,tv_email;
        public Button remove;

        public MyViewHolder(View rootview) {
            super(rootview);
            item_firm_type =  rootview.findViewById(R.id.item_firm_type);
            text_lc_date =  rootview.findViewById(R.id.text_lc_date);
            tv_reg_no =  rootview.findViewById(R.id.tv_reg_no);
            tv_desig =  rootview.findViewById(R.id.tv_desig);
            tv_name =  rootview.findViewById(R.id.tv_name);
            tv_fname =  rootview.findViewById(R.id.tv_fname);
            tv_adhar =  rootview.findViewById(R.id.tv_adhar);
            tv_add =  rootview.findViewById(R.id.tv_add);
            landmark_tv =  rootview.findViewById(R.id.landmark_tv);
            tv_city =  rootview.findViewById(R.id.tv_city);
            tv_dis =  rootview.findViewById(R.id.tv_dis);
            tv_bol =  rootview.findViewById(R.id.tv_bol);
            tv_pin =  rootview.findViewById(R.id.tv_pin);
            tv_mb =  rootview.findViewById(R.id.tv_mb);
            tv_land =  rootview.findViewById(R.id.tv_land);
            tv_email =  rootview.findViewById(R.id.tv_email);
            remove =  rootview.findViewById(R.id.remove);
        }
    }
    public ManufacturerPatnerAdapter(Activity activity, ArrayList<VofficialManufacturePojo> patnerEntities) {
        this.activity = activity;
        layoutInflater = this.activity.getLayoutInflater();
        this.patnerEntities = patnerEntities;

    }
    

    @NonNull
    @Override
    public ManufacturerPatnerAdapter.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partner_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  ManufacturerPatnerAdapter.MyViewHolder viewHolder, int position) {
        VofficialManufacturePojo vofficialManufacturePojo=patnerEntities.get(position);
        viewHolder.item_firm_type.setText("N/A");
        //item_firm_type.setText(new DataBaseHelper(activity).getFirmTypeByID(vofficialManufacturePojo.));
        viewHolder.text_lc_date.setText("N/A");
        viewHolder.tv_reg_no.setText(""+((vofficialManufacturePojo.nominated)?"Y":"N"));
        viewHolder.tv_desig.setText(""+new DataBaseHelper(activity).getDesignationName(String.valueOf(vofficialManufacturePojo.designation)));
        viewHolder.tv_name.setText(""+vofficialManufacturePojo.name);
        viewHolder.tv_fname.setText(""+vofficialManufacturePojo.father);
        viewHolder.tv_adhar.setText(""+vofficialManufacturePojo.aadharNo);
        viewHolder.tv_add.setText(""+vofficialManufacturePojo.address1+" "+vofficialManufacturePojo.address2);
        viewHolder.landmark_tv.setText(""+vofficialManufacturePojo.landmark);
        viewHolder.tv_city.setText(""+vofficialManufacturePojo.city);
        viewHolder.tv_dis.setText((new DataBaseHelper(activity).getDistrictByID(String.valueOf(vofficialManufacturePojo.district))).getName());
        viewHolder.tv_bol.setText(""+(new DataBaseHelper(activity).getBlockByID(String.valueOf(vofficialManufacturePojo.block))).getName());
        viewHolder.tv_pin.setText(""+vofficialManufacturePojo.pincode);
        viewHolder.tv_mb.setText(""+vofficialManufacturePojo.mobile);
        viewHolder.tv_land.setText(""+vofficialManufacturePojo.landline);
        viewHolder.tv_email.setText(""+vofficialManufacturePojo.email);
        viewHolder.remove.setVisibility(View.GONE);
        viewHolder.setIsRecyclable(false);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return patnerEntities.size();
    }

   
   
}
