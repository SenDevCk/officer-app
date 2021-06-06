package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.nic.lmd.adapters.MarketInspectionEntryAdapter;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.MarketInspectionDetail;
import org.nic.lmd.entities.MarketInspectionTab;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.InstrumentPoso;
import org.nic.lmd.retrofitPojo.MyResponse;
import org.nic.lmd.retrofitPojo.VendorDataResponse;
import org.nic.lmd.retrofitPojo.WeightPoso;
import org.nic.lmd.services.WebServiceHelper;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.Utiilties;
import org.nic.lmd.utilities.WebHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MarketInspectionDetailsEntryActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    MarketInspectionEntryAdapter marketInspectionEntryAdapter;
    public static int yearSelected, monthSelected;
    TextView text_year_month;
    Toolbar toolbar;
    Button upload_data;
    public static ArrayList<MarketInspectionTab> marketInspectionTabs;
    public static List<MarketInspectionDetail> marketInspectionDetails;
    public static List<MarketInspectionDetail> marketInspectionDetails_entry = new ArrayList<>();
    boolean[] tabs_selected;
    private String subDiv="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_inspection_details_entry);
        toolbar = findViewById(R.id.toolbar_mar_ins);
        toolbar.setTitle("Market Inspection");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketInspectionDetailsEntryActivity.super.onBackPressed();
            }
        });
        subDiv=getIntent().getStringExtra("subDiv");
        marketInspectionDetails_entry.clear();
        marketInspectionTabs = new DataBaseHelper(MarketInspectionDetailsEntryActivity.this).getMarketInspectionTabs();
        tabs_selected = new boolean[marketInspectionTabs.size()];
        tabs_selected[0] = true;
        upload_data = findViewById(R.id.upload_data);
        upload_data.setVisibility(View.GONE);
        upload_data.setOnClickListener(this);
        tabLayout = findViewById(R.id.into_tab_layout);
        viewPager = findViewById(R.id.view_pager);
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
                marketInspectionDetails_entry.clear();
                GlobalVariable.m_id = 0;
                GlobalVariable.m_id = Long.parseLong("" + String.valueOf(yearSelected).substring(2, 4) + ((String.valueOf(monthSelected).length() == 1) ? "0" + monthSelected : "" + monthSelected) + ((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv)) + "0000");
                if (monthSelected == 4) {
                    upload_data.setVisibility(View.VISIBLE);
                    populateTabs();
                } else {
                    upload_data.setVisibility(View.GONE);
                    callServiceForData();
                }
            }
        });
    }

    APIInterface apiInterface;
    ProgressDialog progressDialog;

    private void callServiceForData() {
        Call<MyResponse<List<MarketInspectionDetail>>> call1 = null;
        progressDialog = new ProgressDialog(MarketInspectionDetailsEntryActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
        if (monthSelected == 1) call1 = apiInterface.doGetMarketInspectionDetails(12, yearSelected - 1, (subDiv.equals("")) ? "187" : subDiv);
        else call1 = apiInterface.doGetMarketInspectionDetails(monthSelected - 1, yearSelected, (subDiv.equals("")) ? "187" : subDiv);
        call1.enqueue(new Callback<MyResponse<List<MarketInspectionDetail>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<MarketInspectionDetail>>> call, Response<MyResponse<List<MarketInspectionDetail>>> response) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getStatusCode() == 200) {
                        marketInspectionDetails = response.body().getData();
                        upload_data.setVisibility(View.VISIBLE);
                        populateTabs();
                    } else {
                        upload_data.setVisibility(View.GONE);
                        Toast.makeText(MarketInspectionDetailsEntryActivity.this, "" + response.body().getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<MarketInspectionDetail>>> call, Throwable t) {
                Log.e("error", t.getMessage());
                if (progressDialog.isShowing()) progressDialog.dismiss();
                Toast.makeText(MarketInspectionDetailsEntryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    public void populateTabs() {
        if (viewPager.getChildCount() > 0) viewPager.invalidate();
        if (tabLayout.getTabCount() > 0) tabLayout.removeAllTabs();
        marketInspectionEntryAdapter = new MarketInspectionEntryAdapter(MarketInspectionDetailsEntryActivity.this, "0", marketInspectionTabs,subDiv);
        viewPager.setAdapter(marketInspectionEntryAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText("")).attach();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("selectedTab", String.valueOf(tab.getPosition()));
                tabs_selected[tab.getPosition()] = true;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void uploadData() {
        if (marketInspectionDetails_entry.size() <= 0) {
            Toast.makeText(this, "No data found !", Toast.LENGTH_SHORT).show();
        } else if (!isAllTabSelected()) {
            Toast.makeText(this, "Scroll left and please fill All tabs", Toast.LENGTH_SHORT).show();
        } else if (!Utiilties.isOnline(MarketInspectionDetailsEntryActivity.this)) {
            Toast.makeText(this, "Please go online !", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog = new ProgressDialog(MarketInspectionDetailsEntryActivity.this);
            progressDialog.setTitle("Upload...");
            progressDialog.show();
            apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
            Call<MyResponse<String>> call1 = apiInterface.saveMarketInspectionDetails(marketInspectionDetails_entry);
            call1.enqueue(new Callback<MyResponse<String>>() {
                @Override
                public void onResponse(Call<MyResponse<String>> call, Response<MyResponse<String>> response) {
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    MyResponse<String> myResponse = null;
                    if (response.body() != null) myResponse = response.body();
                    if (myResponse == null) {
                        Toast.makeText(MarketInspectionDetailsEntryActivity.this, "Null Response found !", Toast.LENGTH_SHORT).show();
                    } else if (myResponse.getStatusCode() == 200) {
                        Toast.makeText(MarketInspectionDetailsEntryActivity.this, "Data Successfully Sent", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(MarketInspectionDetailsEntryActivity.this, "" + myResponse.getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                    Log.e("error", t.getMessage());
                    Toast.makeText(MarketInspectionDetailsEntryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    call.cancel();
                }
            });
        }
    }

    private boolean isAllTabSelected() {
        boolean isAllSelected = true;
        for (int i = 0; i < tabs_selected.length; i++) {
            if (tabs_selected[i] == false) {
                isAllSelected = false;
                tabLayout.selectTab(tabLayout.getTabAt(i));
                break;
            }
        }
        return isAllSelected;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.upload_data) {
            uploadData();
        }
    }
}