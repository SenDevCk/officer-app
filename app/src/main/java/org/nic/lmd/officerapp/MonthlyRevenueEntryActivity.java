package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;

import org.nic.lmd.adapters.RevenueReportItemAdapter;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.entities.RevenueMonthlyTarget;
import org.nic.lmd.entities.RevenueReportEntity;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.MyResponse;
import org.nic.lmd.retrofitPojo.RequestForRevenueData;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.Utiilties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthlyRevenueEntryActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    NestedScrollView nest_ll;
    UserData userData;
    TextView text_vf_previous_tot,text_vf_current_tot,text_vf_tot_sum_tot,text_af_previous_tot,text_af_current_tot,text_af_tot_sum_tot;
    TextView text_cf_previous_tot,text_cf_current_tot,text_cf_total_sum_tot,text_gt_pr_mon,text_gt_cr_mon,text_gt_tot_sum;

    public static int yearSelected, monthSelected;
    TextView text_year_month;
    Toolbar toolbar;
    Button upload_data;
    public static ArrayList<MarketInspectionTab> marketInspectionTabs;
    public static List<RevenueReportEntity> revenueReportEntities;
    public static List<RevenueReportEntity> revenueReportEntities_entry = new ArrayList<>();
    EditText edit_vf_tar,edit_af_tar,edit_cf_tar,edit_lic_fee,edit_reg_fee;
    private String subDiv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_revenue_entry);
        toolbar = findViewById(R.id.toolbar_rev);
        toolbar.setTitle("Revenue Report");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthlyRevenueEntryActivity.super.onBackPressed();
            }
        });
        subDiv=getIntent().getStringExtra("subDiv");
        revenueReportEntities_entry.clear();
        marketInspectionTabs = new DataBaseHelper(MonthlyRevenueEntryActivity.this).getMarketInspectionTabs();
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
        nest_ll=findViewById(R.id.nest_ll);
        nest_ll.setNestedScrollingEnabled(false);
        nest_ll.setVisibility(View.GONE);
        recyclerView=findViewById(R.id.recycler_mar_ins_entry);

        text_vf_previous_tot = findViewById(R.id.text_vf_previous_tot);
        text_vf_current_tot =findViewById(R.id.text_vf_current_tot);
        text_vf_tot_sum_tot = findViewById(R.id.text_vf_tot_sum_tot);

        text_af_previous_tot = findViewById(R.id.text_af_previous_tot);
        text_af_current_tot = findViewById(R.id.text_af_current_tot);
        text_af_tot_sum_tot = findViewById(R.id.text_af_tot_sum_tot);

        text_cf_previous_tot = findViewById(R.id.text_cf_previous_tot);
        text_cf_current_tot = findViewById(R.id.text_cf_current_tot);
        text_cf_total_sum_tot = findViewById(R.id.text_cf_total_sum_tot);

        text_gt_pr_mon = findViewById(R.id.text_gt_pr_mon);
        text_gt_cr_mon=findViewById(R.id.text_gt_cr_mon);
        text_gt_tot_sum=findViewById(R.id.text_gt_tot_sum);

        edit_vf_tar = findViewById(R.id.edit_vf_tar);
        edit_af_tar=findViewById(R.id.edit_af_tar);
        edit_cf_tar=findViewById(R.id.edit_cf_tar);
        edit_lic_fee=findViewById(R.id.edit_lic_fee);
        edit_reg_fee=findViewById(R.id.edit_reg_fee);
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
                revenueReportEntities_entry.clear();
                GlobalVariable.m_id=0;
                //GlobalVariable.m_id = Long.parseLong((String.valueOf(monthSelected).length() == 1) ? "" + String.valueOf(yearSelected).substring(1, 3) + "0" + monthSelected + (userData.getEstbSubdivId().equals("" ) ? 187 : userData.getEstbSubdivId()) + "000" : "" + String.valueOf(yearSelected).substring(1, 3) + "" + (userData.getEstbSubdivId().equals("" ) ? 187 : userData.getEstbSubdivId()) + "000" );
                GlobalVariable.m_id=Long.parseLong(""+String.valueOf(yearSelected).substring(2, 4)+((String.valueOf(monthSelected).length() == 1)?"0"+monthSelected:""+monthSelected)+((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv))+"000");
                if (monthSelected == 4) {
                    upload_data.setVisibility(View.VISIBLE);
                    populateRecycler();
                } else {
                    upload_data.setVisibility(View.GONE);
                    callServiceForData();
                }
            }
        });
    }

    public void populateRecycler() {
        nest_ll.setVisibility(View.VISIBLE);
        recyclerView.invalidate();
        RevenueReportItemAdapter.listenForSum(new RevenueReportItemAdapter.SumListener() {
            @Override
            public void success() {
                showGrandTotal();
            }
        });
        RevenueReportItemAdapter revenueReportItemAdapter = new RevenueReportItemAdapter(MonthlyRevenueEntryActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MonthlyRevenueEntryActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(revenueReportItemAdapter);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ( bottom < oldBottom) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(bottom);
                        }
                    }, 100);
                }
            }
        });
        showGrandTotal();
    }

    @Override
    public void onClick(View v) {
        uploadData();
    }

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    private void callServiceForData() {
        Call<MyResponse<RequestForRevenueData>> call1=null;
        progressDialog=new ProgressDialog(MonthlyRevenueEntryActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
        if (monthSelected == 1) call1 = apiInterface.doGetRevenueReportDetails(12,yearSelected-1,(subDiv.equals(""))?"187":subDiv);
        else call1 = apiInterface.doGetRevenueReportDetails(monthSelected-1,yearSelected,(subDiv.equals(""))?"187":subDiv);
        call1.enqueue(new Callback<MyResponse<RequestForRevenueData>>() {
            @Override
            public void onResponse(Call<MyResponse<RequestForRevenueData>> call, Response<MyResponse<RequestForRevenueData>> response) {
                if (progressDialog.isShowing())progressDialog.dismiss();
                if (response.body()!=null) {
                    if (response.body().getStatusCode()==200){
                        revenueReportEntities = response.body().getData().getRevenueReportEntities_entry();
                        upload_data.setVisibility(View.VISIBLE);
                       /* RevenueMonthlyTarget revenueMonthlyTarget=response.body().getData().getRevenueMonthlyTarget();
                        edit_vf_tar.setText(""+revenueMonthlyTarget.getVf_target());
                        edit_af_tar.setText(""+revenueMonthlyTarget.getAf_target());
                        edit_cf_tar.setText(""+revenueMonthlyTarget.getCf_target());*/
                        populateRecycler();
                    }
                    else {
                        upload_data.setVisibility(View.GONE);
                        Toast.makeText(MonthlyRevenueEntryActivity.this, ""+response.body().getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<MyResponse<RequestForRevenueData>> call, Throwable t) {
                Log.e("error",t.getMessage());
                if (progressDialog.isShowing())progressDialog.dismiss();
                Toast.makeText(MonthlyRevenueEntryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void uploadData() {
        if (isEmpty(edit_vf_tar)){
            Toast.makeText(this, "Enter VF target Amount !", Toast.LENGTH_SHORT).show();
        }
        else if (Double.parseDouble(edit_vf_tar.getText().toString().trim())<=0){
            Toast.makeText(this, "Enter VF target Amount !", Toast.LENGTH_SHORT).show();
        }
        else if (isEmpty(edit_af_tar)){
            Toast.makeText(this, "Enter AF target Amount !", Toast.LENGTH_SHORT).show();
        }
        else if (Double.parseDouble(edit_af_tar.getText().toString().trim())<=0){
            Toast.makeText(this, "Enter AF target Amount !", Toast.LENGTH_SHORT).show();
        }
        else if (isEmpty(edit_cf_tar)){
            Toast.makeText(this, "Enter CF target Amount !", Toast.LENGTH_SHORT).show();
        }
        else if (Double.parseDouble(edit_cf_tar.getText().toString().trim())<=0){
            Toast.makeText(this, "Enter CF target Amount !", Toast.LENGTH_SHORT).show();
        }
        else if (isEmpty(edit_lic_fee)){
            Toast.makeText(this, "Enter Licence Fee !", Toast.LENGTH_SHORT).show();
        }
        else if (Double.parseDouble(edit_lic_fee.getText().toString().trim())<=0){
            Toast.makeText(this, "Enter Valid Licence Fee !", Toast.LENGTH_SHORT).show();
        }
        else if (isEmpty(edit_reg_fee)){
            Toast.makeText(this, "Enter Registration Fee !", Toast.LENGTH_SHORT).show();
        }
        else if (Double.parseDouble(edit_reg_fee.getText().toString().trim())<=0){
            Toast.makeText(this, "Enter valid Registration Fee !", Toast.LENGTH_SHORT).show();
        }
        else if (revenueReportEntities_entry.size()<=0){
            Toast.makeText(this, "No data found !", Toast.LENGTH_SHORT).show();
        }
        else if (!Utiilties.isOnline(MonthlyRevenueEntryActivity.this)){
            Toast.makeText(this, "Please go online !", Toast.LENGTH_SHORT).show();
        }else{
            RevenueMonthlyTarget revenueMonthlyTarget=new RevenueMonthlyTarget();
            revenueMonthlyTarget.setTar_id(Long.parseLong(""+String.valueOf(yearSelected).substring(2, 4)+((String.valueOf(monthSelected).length() == 1)?"0"+monthSelected:""+monthSelected)+((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv))));
            revenueMonthlyTarget.setVf_target(Double.parseDouble(edit_vf_tar.getText().toString().trim()));
            revenueMonthlyTarget.setAf_target(Double.parseDouble(edit_af_tar.getText().toString().trim()));
            revenueMonthlyTarget.setCf_target(Double.parseDouble(edit_cf_tar.getText().toString().trim()));
            revenueMonthlyTarget.setLic_rev_fee(Double.parseDouble(edit_lic_fee.getText().toString().trim()));
            revenueMonthlyTarget.setReg_fee(Double.parseDouble(edit_reg_fee.getText().toString().trim()));
            revenueMonthlyTarget.setSubDiv(((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv)));
            revenueMonthlyTarget.setTMonth(monthSelected);
            revenueMonthlyTarget.setTYear(yearSelected);
            RequestForRevenueData re=new RequestForRevenueData();
            re.setRevenueReportEntities_entry(revenueReportEntities_entry);
            re.setRevenueMonthlyTarget(revenueMonthlyTarget);
            progressDialog=new ProgressDialog(MonthlyRevenueEntryActivity.this);
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
                        Toast.makeText(MonthlyRevenueEntryActivity.this, "Null Response found !", Toast.LENGTH_SHORT).show();
                    }
                    else if (myResponse.getStatusCode()==200) {
                        Toast.makeText(MonthlyRevenueEntryActivity.this, "Data Successfully Sent", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(MonthlyRevenueEntryActivity.this, ""+myResponse.getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                    Log.e("error",t.getMessage());
                    Toast.makeText(MonthlyRevenueEntryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())progressDialog.dismiss();
                    call.cancel();
                }
            });
        }
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
    private void showGrandTotal(){
        double pre_vf=0.0,pr_af=0.0,pr_cf=0.0,cu_vf=0.0,cu_af=0.0,cu_cf=0.0,tot_sum_vf=0.0,total_sum_af=0.0
                ,tot_sum_cf=0.0,gr_tot1=0.0,gr_tot2=0.0,gr_tot3=0.0;
        for (RevenueReportEntity revenueReportEntity :MonthlyRevenueEntryActivity.revenueReportEntities_entry){
                pre_vf+=(revenueReportEntity.getVf_total_current()-revenueReportEntity.getVf_current());
                pr_af+=(revenueReportEntity.getAf_total_current()-revenueReportEntity.getAf_current());
                pr_cf+=(revenueReportEntity.getCf_total_current()-revenueReportEntity.getCf_current());
                cu_vf+=revenueReportEntity.getVf_current();
                cu_af+=revenueReportEntity.getAf_current();
                cu_cf+=revenueReportEntity.getCf_current();
                tot_sum_vf+=revenueReportEntity.getVf_total_current();
                total_sum_af+=revenueReportEntity.getAf_total_current();
                tot_sum_cf+=revenueReportEntity.getCf_total_current();
        }
        text_vf_current_tot.setText(""+cu_vf);
        text_af_current_tot.setText(""+cu_af);
        text_cf_current_tot.setText(""+cu_cf);
        text_vf_previous_tot.setText(""+pre_vf);
        text_af_previous_tot.setText(""+pr_af);
        text_cf_previous_tot.setText(""+pr_cf);
        text_vf_tot_sum_tot.setText(""+tot_sum_vf);
        text_af_tot_sum_tot.setText(""+total_sum_af);
        text_cf_total_sum_tot.setText(""+tot_sum_cf);
        gr_tot1=pre_vf+pr_af+pr_cf;
        gr_tot2=cu_vf+cu_af+cu_cf;
        gr_tot3=tot_sum_vf+total_sum_af+tot_sum_cf;
        text_gt_pr_mon.setText(""+gr_tot1);
        text_gt_cr_mon.setText(""+gr_tot2);
        text_gt_tot_sum.setText(""+gr_tot3);
    }
}