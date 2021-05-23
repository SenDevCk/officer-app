package org.nic.lmd.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.nic.lmd.officerapp.R;


/**
 * Created by chandan on 30.09.2020
 */

public class BillingDetailsAdapter extends RecyclerView.Adapter<BillingDetailsAdapter.MyViewHolder> {

    private JSONObject jsonObject;
    Activity activity;
    private int lastPosition = -1;
    int count_for_ins;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_tag,text_name, text_cat, text_qty, text_valid, text_cur_qtr, text_val_yr, text_vf_rate, text_vf, text_ur, text_af;
        public MyViewHolder(View view) {
            super(view);
            text_name =  view.findViewById(R.id.text_name);
            text_cat =  view.findViewById(R.id.text_cat);
            text_qty =  view.findViewById(R.id.text_qty);
            text_valid =  view.findViewById(R.id.text_valid);
            text_cur_qtr =  view.findViewById(R.id.text_cur_qtr);
            text_val_yr =  view.findViewById(R.id.text_val_yr);
            text_vf_rate =  view.findViewById(R.id.text_vf_rate);
            text_vf =  view.findViewById(R.id.text_vf);
            text_ur =  view.findViewById(R.id.text_ur);
            text_af =  view.findViewById(R.id.text_af);
            text_tag =  view.findViewById(R.id.text_tag);
        }
    }


    public BillingDetailsAdapter(JSONObject jsonObject, Activity activity) {
        this.jsonObject = jsonObject;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billing_detail_item1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        int count_j1 = 0;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("weights");
            JSONArray jsonArray2 = jsonObject.getJSONArray("instruments");
            count_j1 = jsonArray2.length() ;
            if (count_j1>position){
                if(count_for_ins==0){
                   holder.text_tag.setText("Instruments");
                    holder.text_tag.setVisibility(View.VISIBLE);
                }
                JSONObject json_ins=jsonArray2.getJSONObject(count_for_ins);
                holder.text_name.setText(""+json_ins.getString("capacityValue"));
                holder.text_cat.setText(""+json_ins.getString("categoryName"));
                holder.text_qty.setText(""+json_ins.getString("quantity"));
                holder.text_valid.setText(""+json_ins.getString("validYear")+" yr");
                holder.text_cur_qtr.setText(""+json_ins.getString("currentQtr"));
                holder.text_val_yr.setText(""+json_ins.getString("nextverificationQtrName"));
                holder.text_vf_rate.setText(""+json_ins.getString("vfRate"));
                holder.text_vf.setText(""+json_ins.getString("vfAmount"));
                holder.text_ur.setText(""+json_ins.getString("urAmount"));
                holder.text_af.setText(""+json_ins.getString("afAmount"));
                if (jsonArray2.length()-1==count_for_ins)
                    count_for_ins=0;
                else count_for_ins++;
            }else{

                if(count_for_ins==0){
                    holder.text_tag.setText("Weights");
                    holder.text_tag.setVisibility(View.VISIBLE);
                }
                JSONObject json_wt=jsonArray.getJSONObject(count_for_ins);
                holder.text_name.setText(""+json_wt.getString("denominationValue"));
                holder.text_cat.setText(""+json_wt.getString("categoryName"));
                holder.text_qty.setText(""+json_wt.getString("quantity"));
                holder.text_valid.setText(""+json_wt.getString("validYear")+" yr");
                holder.text_cur_qtr.setText(""+json_wt.getString("currentQtr"));
                holder.text_val_yr.setText(""+json_wt.getString("nextverificationQtrName"));
                holder.text_vf_rate.setText(""+json_wt.getString("vfRate"));
                holder.text_vf.setText(""+json_wt.getString("vfAmount"));
                holder.text_ur.setText(""+json_wt.getString("urAmount"));
                holder.text_af.setText(""+json_wt.getString("afAmount"));
                if (jsonArray.length()-1==count_for_ins)
                     count_for_ins=0;
                else count_for_ins++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       setAnimation(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        int count = 0;
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("weights");
            JSONArray jsonArray2 = jsonObject.getJSONArray("instruments");
            count = jsonArray.length() + jsonArray2.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}