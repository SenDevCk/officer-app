package org.nic.lmd.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.StatusForRenewalData;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.officerapp.VerificationLMOActivity;
import org.nic.lmd.retrofitPojo.InstrumentPoso;
import org.nic.lmd.retrofitPojo.VendorPoso;


/**
 * Created by chandan on 09.01.2021
 */

public class RenewalInstrumentAdapter extends RecyclerView.Adapter<RenewalInstrumentAdapter.MyViewHolder> {

    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_name, text_cat, text_vcid, text_qty, text_cur_qtr, text_next_vc_qtr;
        public ToggleButton toggle_request_for_vc, toggle_delete;
        public RadioGroup redio_group_action;

        public MyViewHolder(View view) {
            super(view);
            text_name = view.findViewById(R.id.text_name);
            text_cat = view.findViewById(R.id.text_cat);
            text_vcid = view.findViewById(R.id.text_vcid);
            text_qty = view.findViewById(R.id.text_qty);
            text_cur_qtr = view.findViewById(R.id.text_cur_qtr);
            text_next_vc_qtr = view.findViewById(R.id.text_next_vc_qtr);
            toggle_request_for_vc = view.findViewById(R.id.toggle_request_for_vc);
            toggle_delete = view.findViewById(R.id.toggle_delete);
            redio_group_action = view.findViewById(R.id.redio_group_action);

        }
    }


    public RenewalInstrumentAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.renewal_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //RenawalActivity.jsonObject.getJSONArray("weights").getJSONObject(position).accumulate("action", "R");
        VerificationLMOActivity.statusForRenewalDataInstrument.add(new StatusForRenewalData());
        final InstrumentPoso instrument = VerificationLMOActivity.vendorDataResponse.vendorPoso.instruments.get(position);
        DataBaseHelper db = new DataBaseHelper(activity);
        holder.text_name.setText("" + db.getINSCapByID(String.valueOf(instrument.capacityId)).getCapacityDesc());
        holder.text_cat.setText("" + db.getINSCatByID(String.valueOf(instrument.categoryId)).getName());
        holder.text_vcid.setText("" + instrument.vcId);
        instrument.currentQtr = (instrument.currentQtr == null) ? "N/A" : instrument.currentQtr;
        holder.text_cur_qtr.setText("" + instrument.currentQtr);
        instrument.nextverificationQtr = (instrument.nextverificationQtr == null) ? "N/A" : instrument.nextverificationQtr;
        holder.text_next_vc_qtr.setText("" + instrument.nextverificationQtr);
        if (instrument.status != null) {
            if (instrument.status.equals("D")) {
                holder.toggle_request_for_vc.setClickable(true);
                holder.toggle_request_for_vc.setChecked(true);
                holder.toggle_request_for_vc.setClickable(false);
            }
        }
        holder.toggle_request_for_vc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((RadioButton) holder.redio_group_action.getChildAt(0)).setChecked(isChecked);
                if (isChecked) {
                    holder.toggle_delete.setClickable(true);
                    holder.toggle_delete.setChecked(false);
                    holder.toggle_delete.setClickable(false);
                } else {
                    holder.toggle_delete.setClickable(true);
                    //holder.redio_group_action.setClickable(false);
                }
                VerificationLMOActivity.statusForRenewalDataInstrument.get(position).setRenewed(isChecked);
                //VerificationLMOActivity.vendorDataResponse.vendorPoso.weights.get(position).mCheck= isChecked;
            }
        });

        holder.toggle_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    holder.toggle_request_for_vc.setChecked(false);
                    holder.toggle_request_for_vc.setClickable(false);
                } else {
                    holder.toggle_request_for_vc.setClickable(true);
                }
                VerificationLMOActivity.statusForRenewalDataInstrument.get(position).setDeleted(isChecked);
            }
        });

            holder.redio_group_action.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    try {
                        if (checkedId == R.id.radio_renew) {
                            //RenawalActivity.jsonObject.getJSONArray("weights").getJSONObject(position).put("isDeleted", isChecked);
                        } else if (checkedId == R.id.radio_repair) {

                        } else if (checkedId == R.id.radio_unrep) {

                        } else {

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        holder.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return VerificationLMOActivity.vendorDataResponse.vendorPoso.instruments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}