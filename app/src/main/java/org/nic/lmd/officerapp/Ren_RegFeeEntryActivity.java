package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.nic.lmd.adapters.RevenueReportItemAdapter;
import org.nic.lmd.entities.NatureOfBusiness;
import org.nic.lmd.entities.RevenueReportEntity;
import org.nic.lmd.entities.SubDivision;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;

public class Ren_RegFeeEntryActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edit_m_current,edit_d_current,edit_r_current,edit_p_current;
    TextView text_m_previous,text_d_previous,text_r_previous,text_p_previous;
    TextView text_m_tot_sum,text_d_tot_sum,text_r_tot_sum,text_p_tot_sum;
    TextView text_row_tot1,text_row_tot2,text_row_tot3,text_row_tot4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_reg_fee_entry);
        toolbar = findViewById(R.id.toolbar_ren_rev_entry);
        toolbar.setTitle("Reneval-Registration Fee Entry");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ren_RegFeeEntryActivity.super.onBackPressed();
            }
        });
        edit_m_current=findViewById(R.id.edit_m_current);
        edit_d_current=findViewById(R.id.edit_d_current);
        edit_r_current=findViewById(R.id.edit_r_current);
        edit_p_current=findViewById(R.id.edit_p_current);

        edit_m_current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_d_current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_r_current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_p_current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void addTextChange(EditText editText, final int position, char type) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edit_m_current.getText().toString().trim().equals("")&&!edit_d_current.getText().toString().trim().equals("")&&!edit_d_current.getText().toString().trim().equals("")&&!edit_p_current.getText().toString().trim().equals("")) {
                    if (Double.parseDouble(edit_m_current.getText().toString().trim()) >= 0 && Double.parseDouble(edit_d_current.getText().toString().trim()) >= 0 && Double.parseDouble(edit_r_current.getText().toString().trim()) >= 0 && Double.parseDouble(edit_r_current.getText().toString().trim()) >= 0) {
                        //holder.text_total_sum.setText(""+(Long.parseLong(s.toString())+Long.parseLong(holder.edit_previous.getText().toString().trim())));
                        RevenueReportEntity revenueReportEntity_pre=MonthlyRevenueEntryActivity.revenueReportEntities_entry.stream().filter((re)->natureOfBusiness.getId().equals(re.getType_of_bussiness().getId())).findAny().orElse(new RevenueReportEntity());
                        //RevenueReportEntity revenueReportEntity = (MonthlyRevenueEntryActivity.revenueReportEntities_entry.size() > (position)) ? MonthlyRevenueEntryActivity.revenueReportEntities_entry.get(position) : new RevenueReportEntity();
                        RevenueReportEntity revenueReportEntity=revenueReportEntity_pre;
                        revenueReportEntity.setMonth(MonthlyRevenueEntryActivity.monthSelected);
                        revenueReportEntity.setYear(MonthlyRevenueEntryActivity.yearSelected);
                        revenueReportEntity.setVf_current(Long.parseLong(holder.edit_vf_current.getText().toString().trim()));
                        revenueReportEntity.setAf_current(Long.parseLong(holder.edit_af_current.getText().toString().trim()));
                        revenueReportEntity.setCf_current(Long.parseLong(holder.edit_cf_current.getText().toString().trim()));
                        revenueReportEntity.setVf_total_current(revenueReportEntity.getVf_current() + Double.parseDouble(holder.text_vf_previous.getText().toString().trim()));
                        revenueReportEntity.setAf_total_current(revenueReportEntity.getAf_current() + Double.parseDouble(holder.text_af_previous.getText().toString().trim()));
                        revenueReportEntity.setCf_total_current(revenueReportEntity.getCf_current() + Double.parseDouble(holder.text_cf_previous.getText().toString().trim()));
                        SubDivision sub = new SubDivision();
                        sub.setId((subDiv.equals("" )) ? 187 : Integer.parseInt(subDiv));
                        revenueReportEntity.setSub_div(sub);
                        UserData userData= CommonPref.getUserDetails(activity);
                        revenueReportEntity.setUser_id(userData.getUserid());
                        revenueReportEntity.setType_of_bussiness(natureOfBusiness);
                        if (revenueReportEntity.getRev_rep_id() == 0) {
                            GlobalVariable.m_id++;
                            revenueReportEntity.setRev_rep_id(GlobalVariable.m_id);
                            MonthlyRevenueEntryActivity.revenueReportEntities_entry.add(revenueReportEntity);
                        } else {
                            int index=MonthlyRevenueEntryActivity.revenueReportEntities_entry.indexOf(revenueReportEntity_pre);
                            MonthlyRevenueEntryActivity.revenueReportEntities_entry.set(index, revenueReportEntity);
                            //MonthlyRevenueEntryActivity.revenueReportEntities_entry.set(position, revenueReportEntity);
                        }
                        holder.text_vf_tot_sum.setText(""+revenueReportEntity.getVf_total_current());
                        holder.text_af_tot_sum.setText(""+revenueReportEntity.getAf_total_current());
                        holder.text_cf_total_sum.setText(""+revenueReportEntity.getCf_total_current());
                        calculateTotal(holder);
                        sumlisten.success();
                    }
                }else{
                    Log.e("e-log","blank data");
                    ;                }

            }
        });
        editText.setText("0" );
    }

}