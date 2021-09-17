package org.nic.lmd.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.MarketInspectionDetail;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.entities.NatureOfBusiness;
import org.nic.lmd.entities.SubDivision;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.officerapp.MarketInspectionDetailsEntryActivity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;

import java.util.List;


/**
 * Created by chandan on 24.02.2021
 */

public class MarketInspectionItemAdapter extends RecyclerView.Adapter<MarketInspectionItemAdapter.MyViewHolder> {

    Activity activity;
    String subDiv;
    List<NatureOfBusiness> premisesTypeEntities;
    MarketInspectionTab mParam1;
    int mParam2;
    boolean isPrevious,isDataFound;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_premisses,text_name, text_total_sum,edit_previous;
        public EditText  edit_current;

        public MyViewHolder(View view) {
            super(view);
            text_premisses = view.findViewById(R.id.text_premisses);
            edit_previous = view.findViewById(R.id.edit_previous);
            edit_current = view.findViewById(R.id.edit_current);
            text_total_sum = view.findViewById(R.id.text_total_sum);
        }
    }


    public MarketInspectionItemAdapter(Activity activity, MarketInspectionTab mParam1, int mParam2 , String subDiv,boolean isPrevious,boolean isDataFound) {
        this.activity = activity;
        this.subDiv = subDiv;
        this.premisesTypeEntities = new DataBaseHelper(activity).getNatureofBusiness();
        this.mParam1=mParam1;
        this.mParam2=mParam2;
        this.isPrevious=isPrevious;
        this.isDataFound=isDataFound;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.market_ins_entry_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final NatureOfBusiness premisesTypeEntity = premisesTypeEntities.get(position);
        //DataBaseHelper db = new DataBaseHelper(activity);
        holder.text_premisses.setText("" + premisesTypeEntity.getValue());
        addTextChangeListener(holder, premisesTypeEntity);
        addFocusChangeListner(holder.edit_current);
        MarketInspectionDetail marketInspectionDetail_pre = null;
        //if (!MarketInspectionDetailsEntryActivity.tabs_selected[mParam2]) {
        if (MarketInspectionDetailsEntryActivity.marketInspectionDetails_entry.size()<((mParam2+1)*premisesTypeEntities.size())) {
            if (isDataFound) {
                marketInspectionDetail_pre = MarketInspectionDetailsEntryActivity.marketInspectionDetails.stream()
                        .filter((p) -> mParam1.getMarket_ins_id() == p.mar_ins_type.getMarket_ins_id() && p.nature_of_business.getId().equals(premisesTypeEntity.getId()))
                        .findAny()
                        .orElse(new MarketInspectionDetail());
            } else {
                marketInspectionDetail_pre = new MarketInspectionDetail();
            }
            holder.text_total_sum.setText("0");

            if (isPrevious) {
                holder.edit_previous.setText("" + (marketInspectionDetail_pre.previous_accu_count));
                holder.edit_current.setText("0");
            } else {
                holder.edit_previous.setText("" + (marketInspectionDetail_pre.previous_accu_count - marketInspectionDetail_pre.current_count));
                holder.edit_current.setText("" + marketInspectionDetail_pre.current_count);
            }
        }else{
            marketInspectionDetail_pre = MarketInspectionDetailsEntryActivity.marketInspectionDetails_entry.stream()
                    .filter((p) -> mParam1.getMarket_ins_id() == p.mar_ins_type.getMarket_ins_id() && p.nature_of_business.getId().equals(premisesTypeEntity.getId()))
                    .findAny()
                    .orElse(new MarketInspectionDetail());
            holder.edit_previous.setText("" + (marketInspectionDetail_pre.previous_accu_count-marketInspectionDetail_pre.current_count));
            holder.edit_current.setText(""+marketInspectionDetail_pre.current_count);
        }
        holder.setIsRecyclable(false);
    }

    private void addTextChangeListener(MyViewHolder holder,NatureOfBusiness premisesTypeEntity) {
        holder.edit_current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().equals("")){
                    if (Integer.parseInt(s.toString())>=0){
                        UserData userData= CommonPref.getUserDetails(activity);
                        holder.text_total_sum.setText(""+(Long.parseLong(s.toString())+Long.parseLong(holder.edit_previous.getText().toString().trim())));
                        MarketInspectionDetail marketInspectionDetail=MarketInspectionDetailsEntryActivity.marketInspectionDetails_entry.stream()
                                .filter((p) -> mParam1.getMarket_ins_id() == p.mar_ins_type.getMarket_ins_id() && p.nature_of_business.getId().equals(premisesTypeEntity.getId()))
                                .findAny()
                                .orElse(new MarketInspectionDetail());
                        marketInspectionDetail.current_count=Long.parseLong(s.toString());
                        marketInspectionDetail.previous_accu_count=(Long.parseLong(s.toString())+Long.parseLong(holder.edit_previous.getText().toString().trim()));
                        marketInspectionDetail.m_month=MarketInspectionDetailsEntryActivity.monthSelected;
                        marketInspectionDetail.m_year=MarketInspectionDetailsEntryActivity.yearSelected;
                        marketInspectionDetail.sub_div=new SubDivision();
                        marketInspectionDetail.sub_div.setId(Integer.parseInt(subDiv));
                        marketInspectionDetail.user_id=userData.getUserid();
                        marketInspectionDetail.nature_of_business=premisesTypeEntity;
                        marketInspectionDetail.mar_ins_type=mParam1;
                        if (marketInspectionDetail.mmid==0){
                            GlobalVariable.m_id++;
                            marketInspectionDetail.mmid=GlobalVariable.m_id;
                            MarketInspectionDetailsEntryActivity.marketInspectionDetails_entry.add(marketInspectionDetail);
                        }else{
                            int index=MarketInspectionDetailsEntryActivity.marketInspectionDetails_entry.indexOf(marketInspectionDetail);
                            MarketInspectionDetailsEntryActivity.marketInspectionDetails_entry.set(index,marketInspectionDetail);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return premisesTypeEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return position;
    }

    private void addFocusChangeListner(EditText editText){
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                if(editText.getText().toString().trim().equals("0")||editText.getText().toString().trim().equals("0.0")){
                    editText.setText("");
                }
            }else{
                if(editText.getText().toString().trim().equals("")){
                    editText.setText("0");
                }
            }
        });
    }
}