package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import org.nic.lmd.adapters.RevenueReportItemAdapter;
import org.nic.lmd.entities.NatureOfBusiness;
import org.nic.lmd.entities.RenevalAndRegistrationFee;
import org.nic.lmd.entities.RevenueMonthlyTarget;
import org.nic.lmd.entities.RevenueReportEntity;
import org.nic.lmd.entities.SubDivision;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.MyResponse;
import org.nic.lmd.retrofitPojo.RequestForRevenueData;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.Utiilties;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ren_RegFeeEntryActivity extends AppCompatActivity implements View.OnClickListener {

    TextView text_year_month;
    Toolbar toolbar;
    Button upload_data;
    String subDiv="";
    public static int yearSelected, monthSelected;
    EditText edit_m_current,edit_d_current,edit_r_current,edit_p_current;
    TextView text_m_previous,text_d_previous,text_r_previous,text_p_previous;
    TextView text_m_tot_sum,text_d_tot_sum,text_r_tot_sum,text_p_tot_sum;
    TextView text_row_tot1,text_row_tot2,text_row_tot3,text_row_tot4;
    RenevalAndRegistrationFee renevalAndRegistrationFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_reg_fee_entry);
        subDiv=getIntent().getStringExtra("subDiv");
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

        upload_data = findViewById(R.id.upload_data);
        upload_data.setVisibility(View.GONE);
        upload_data.setOnClickListener(this);
        text_year_month = findViewById(R.id.text_year_month);
        text_year_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthYearPicker();
            }
        });
    }
    public void monthYearPicker() {
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);

        MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected);

        dialogFragment.show(getSupportFragmentManager(), null);
        dialogFragment.setOnDateSetListener(new MonthYearPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int year, int monthOfYear) {
                // do something
                yearSelected = year;
                monthSelected = monthOfYear + 1;
                text_year_month.setText("" + monthSelected + "-" + yearSelected);
                text_year_month.setClickable(false);
                GlobalVariable.m_id=0;
                //GlobalVariable.m_id = Long.parseLong((String.valueOf(monthSelected).length() == 1) ? "" + String.valueOf(yearSelected).substring(1, 3) + "0" + monthSelected + (userData.getEstbSubdivId().equals("" ) ? 187 : userData.getEstbSubdivId()) + "000" : "" + String.valueOf(yearSelected).substring(1, 3) + "" + (userData.getEstbSubdivId().equals("" ) ? 187 : userData.getEstbSubdivId()) + "000" );
                GlobalVariable.m_id=Long.parseLong(""+String.valueOf(yearSelected).substring(2, 4)+((String.valueOf(monthSelected).length() == 1)?"0"+monthSelected:""+monthSelected)+((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv)));
                if (monthSelected == 4) {
                    upload_data.setVisibility(View.VISIBLE);
                    populateData();
                } else {
                    upload_data.setVisibility(View.GONE);
                    callServiceForData();
                }
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
                        RenevalAndRegistrationFee revenueReportEntity_pre=Ren_RegFeeEntryActivity.revenueReportEntities_entry.stream().filter((re)->natureOfBusiness.getId().equals(re.getType_of_bussiness().getId())).findAny().orElse(new RevenueReportEntity());
                        //RevenueReportEntity revenueReportEntity = (Ren_RegFeeEntryActivity.revenueReportEntities_entry.size() > (position)) ? Ren_RegFeeEntryActivity.revenueReportEntities_entry.get(position) : new RevenueReportEntity();
                        RevenueReportEntity revenueReportEntity=revenueReportEntity_pre;
                        revenueReportEntity.setMonth(Ren_RegFeeEntryActivity.monthSelected);
                        revenueReportEntity.setYear(Ren_RegFeeEntryActivity.yearSelected);
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
                            Ren_RegFeeEntryActivity.revenueReportEntities_entry.add(revenueReportEntity);
                        } else {
                            int index=Ren_RegFeeEntryActivity.revenueReportEntities_entry.indexOf(revenueReportEntity_pre);
                            Ren_RegFeeEntryActivity.revenueReportEntities_entry.set(index, revenueReportEntity);
                            //Ren_RegFeeEntryActivity.revenueReportEntities_entry.set(position, revenueReportEntity);
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
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    private void callServiceForData() {
        Call<MyResponse<RenevalAndRegistrationFee>> call1=null;
        progressDialog=new ProgressDialog(Ren_RegFeeEntryActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
        if (monthSelected == 1) call1 = apiInterface.doGetRenRegData(12,yearSelected-1,(subDiv.equals(""))?"187":subDiv);
        else call1 = apiInterface.doGetRenRegData(monthSelected-1,yearSelected,(subDiv.equals(""))?"187":subDiv);
        call1.enqueue(new Callback<MyResponse<RenevalAndRegistrationFee>>() {
            @Override
            public void onResponse(Call<MyResponse<RenevalAndRegistrationFee>> call, Response<MyResponse<RenevalAndRegistrationFee>> response) {
                if (progressDialog.isShowing())progressDialog.dismiss();
                if (response.body()!=null) {
                    if (response.body().getStatusCode()==200){
                        renevalAndRegistrationFee = response.body().getData();
                        upload_data.setVisibility(View.VISIBLE);
                        populateData();
                    }
                    else {
                        upload_data.setVisibility(View.GONE);
                        Toast.makeText(Ren_RegFeeEntryActivity.this, ""+response.body().getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<RenevalAndRegistrationFee>> call, Throwable t) {
                Log.e("error",t.getMessage());
                if (progressDialog.isShowing())progressDialog.dismiss();
                Toast.makeText(Ren_RegFeeEntryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
    private void populateData() {
        text_m_previous.setText(renevalAndRegistrationFee.);
        text_d_previous.setText();
        text_r_previous.setText();
        text_p_previous.setText();
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private void uploadData() {
        if (isEmpty(edit_vf_tar)){
            Toast.makeText(this, "Enter VF target Amount !", Toast.LENGTH_SHORT).show();
        }
    else{
            RevenueMonthlyTarget revenueMonthlyTarget=new RevenueMonthlyTarget();
            revenueMonthlyTarget.setTar_id(Long.parseLong(""+String.valueOf(yearSelected).substring(2, 4)+((String.valueOf(monthSelected).length() == 1)?"0"+monthSelected:""+monthSelected)+((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv))));
            revenueMonthlyTarget.setVf_target(Double.parseDouble(edit_vf_tar.getText().toString().trim()));
            revenueMonthlyTarget.setAf_target(Double.parseDouble(edit_af_tar.getText().toString().trim()));
            revenueMonthlyTarget.setCf_target(Double.parseDouble(edit_cf_tar.getText().toString().trim()));
            revenueMonthlyTarget.setLic_ren_fee(Double.parseDouble(edit_lic_fee.getText().toString().trim()));
            revenueMonthlyTarget.setReg_fee(Double.parseDouble(edit_reg_fee.getText().toString().trim()));
            revenueMonthlyTarget.setSubDiv(((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv)));
            revenueMonthlyTarget.setTMonth(monthSelected);
            revenueMonthlyTarget.setTYear(yearSelected);
            RequestForRevenueData re=new RequestForRevenueData();
            re.setRevenueReportEntities_entry(revenueReportEntities_entry);
            re.setRevenueMonthlyTarget(revenueMonthlyTarget);
            progressDialog=new ProgressDialog(Ren_RegFeeEntryActivity.this);
            progressDialog.setTitle("Upload...");
            progressDialog.show();
            apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
            Call<MyResponse<String>> call1 = apiInterface.saveRevenueReport(re);
            call1.enqueue(new Callback<MyResponse<String>>() {
                @Override
                public void onResponse(Call<MyResponse<String>> call, Response<MyResponse<String>> response) {
                    Log.e("elog",""+response.body());
                    if (progressDialog.isShowing())progressDialog.dismiss();
                    MyResponse<String> myResponse=null;
                    if (response.body()!=null) myResponse= response.body();
                    if (myResponse==null){
                        Toast.makeText(Ren_RegFeeEntryActivity.this, "Null Response found !", Toast.LENGTH_SHORT).show();
                    }
                    else if (myResponse.getStatusCode()==200) {
                        Toast.makeText(Ren_RegFeeEntryActivity.this, "Data Successfully Sent", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(Ren_RegFeeEntryActivity.this, ""+myResponse.getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                    Log.e("error",t.getMessage());
                    Toast.makeText(Ren_RegFeeEntryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())progressDialog.dismiss();
                    call.cancel();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        uploadData();
    }
}