package org.nic.lmd.officerapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog;
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment;
import com.google.android.material.navigation.NavigationView;


import org.nic.lmd.adapters.CustomExpandableListAdapter;
import org.nic.lmd.entities.ExpandableListDataPump;
import org.nic.lmd.entities.UserData;


import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.DashboardResponse;
import org.nic.lmd.retrofitPojo.ManufacturerPoso;
import org.nic.lmd.retrofitPojo.VendorPoso;
import org.nic.lmd.utilities.Urls_this_pro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 15;
    Toolbar toolbar;
    ListView list_ven;
    ImageView imageViewheader;
    TextView text_header_name, text_header_mobile;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    DrawerLayout drawer;
    APIInterface apiInterface;
    public static DashboardResponse dashboardResponse;
    static List<VendorPoso> pending_vender, verified_vendor;
    static List<ManufacturerPoso> pending_manu, verified_manu;
    String locationType, loginRole, loginLocation;
    private ProgressDialog dialog;
    UserData userData;
    int yearSelected, monthSelected;
    UserData userinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userData = CommonPref.getUserDetails(MainActivity.this);
        locationType = getIntent().getStringExtra("locationType");
        loginRole = getIntent().getStringExtra("loginRole");
        loginLocation = getIntent().getStringExtra("loginLocation");
        toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            String code_v = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
            TextView app_name_tip = navigationView.findViewById(R.id.app_name_tip);
            app_name_tip.setText(getResources().getString(R.string.app_name) + " ( " + code_v + "." + version + " ) V");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            String code_v = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
            TextView app_name_tip = navigationView.findViewById(R.id.app_name_tip);
            app_name_tip.setText(getResources().getString(R.string.app_name) + " ( " + code_v + "." + version + " ) V");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        View header = navigationView.getHeaderView(0);
        text_header_name = header.findViewById(R.id.text_header_name);
        text_header_mobile = header.findViewById(R.id.text_header_mobile);
        imageViewheader = header.findViewById(R.id.imageViewheader);
        userinfo = CommonPref.getUserDetails(MainActivity.this);
        text_header_name.setText("" + userinfo.getName());
        text_header_mobile.setText("" + userinfo.getContact());
        expandableListView = navigationView.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        populateExpendableList();
        list_ven = findViewById(R.id.list_ven);
        //callServiceForData();
    }

    public void monthYearPicker() {
        Calendar calendar = Calendar.getInstance();
        yearSelected = calendar.get(Calendar.YEAR);
        monthSelected = calendar.get(Calendar.MONTH);

        MonthYearPickerDialogFragment dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected);

        dialogFragment.show(getSupportFragmentManager(), null);
        dialogFragment.setOnDateSetListener((int year, int monthOfYear) -> {
            // do something
            yearSelected = year;
            monthSelected = monthOfYear + 1;
            /*Intent intent=new Intent(MainActivity.this, InspectionentryActivity.class);
            intent.putExtra("year",yearSelected);
            intent.putExtra("month",monthSelected);
            startActivity(intent);*/
            Toast.makeText(MainActivity.this, "Under Progress !", Toast.LENGTH_SHORT).show();
        });
    }

    private void populateExpendableList() {
        expandableListView.invalidate();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            /*Toast.makeText(getApplicationContext(),
                    expandableListTitle.get(groupPosition) + " List Expanded.",
                    Toast.LENGTH_SHORT).show();*/
        });

        expandableListView.setOnGroupCollapseListener(groupPosition -> {
           /* Toast.makeText(getApplicationContext(),
                    expandableListTitle.get(groupPosition) + " List Collapsed.",
                    Toast.LENGTH_SHORT).show();*/

        });

        expandableListView.setOnChildClickListener((ExpandableListView parent, View v,
                                                    int groupPosition, int childPosition, long id) -> {
            //if (drawer.isDrawerOpen(drawer))
            drawer.closeDrawers();
            String name_parent = parent.getExpandableListAdapter().getGroup(groupPosition).toString();
            String name_child = parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();
            //Toast.makeText(MainActivity.this, ""+name_child, Toast.LENGTH_SHORT).show();
                /*if (groupPosition == 0 && childPosition == 0) {
                    Intent intent = new Intent(MainActivity.this, ApplicationListActivity.class);
                    intent.putExtra("which", "pending");
                    startActivity(intent);
                } else if (groupPosition == 0 && childPosition == 1) {
                    Intent intent = new Intent(MainActivity.this, ApplicationListActivity.class);
                    intent.putExtra("which", "verified");
                    startActivity(intent);
                } else if (groupPosition == 1 && childPosition == 0) {
                    Intent intent = new Intent(MainActivity.this, ManufacturerListActivity.class);
                    intent.putExtra("which", "pending");
                    startActivity(intent);
                } else if (groupPosition == 1 && childPosition == 1) {
                    Intent intent = new Intent(MainActivity.this, ManufacturerListActivity.class);
                    intent.putExtra("which", "verified");
                    startActivity(intent);
                } else*/
            switch (name_parent) {
                case "Report":
                    if (name_child.equals("Market Inspection Details Entry")) {
                        if (!loginRole.contains("Inspector")) {
                            Toast.makeText(MainActivity.this, "You are not authorised !", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, MarketInspectionDetailsEntryActivity.class);
                            intent.putExtra("subDiv", loginLocation);
                            startActivity(intent);
                        }
                    } else if (name_child.equals("Revenue Details Entry")) {
                        if (!loginRole.contains("Inspector")) {
                            Toast.makeText(MainActivity.this, "You are not authorised !", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, MonthlyRevenueEntryActivity.class);
                            intent.putExtra("subDiv", loginLocation);
                            startActivity(intent);
                        }
                    } else if (name_child.equals("Renewal/Registration Entry")) {
                        if (!loginRole.contains("Inspector")) {
                            Toast.makeText(MainActivity.this, "You are not authorised !", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, Ren_RegFeeEntryActivity.class);
                            intent.putExtra("subDiv", loginLocation);
                            startActivity(intent);
                        }
                    }
                    else if (name_child.equals("Payment Entry")) {
                        if (!loginRole.contains("Inspector")) {
                            Toast.makeText(MainActivity.this, "You are not authorised !", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, PaymentEntryActivity.class);
                            intent.putExtra("subDiv", loginLocation);
                            startActivity(intent);
                        }
                    }
                    break;

                case "Inspection":
                    if (name_child.equals("Inspection")) {
                        monthYearPicker();
                    }
                    break;

                case "Tools":
                    if (name_child.equals("Scanner")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkAndRequestPermissions()) {
                                goToscanCode();
                            }
                        } else {
                            goToscanCode();
                        }
                    } else{
                        Toast.makeText(MainActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Settings":
                    if (name_child.equals("Logout")) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Really Logout ?")
                                .setMessage("Are you sure want to logout ?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        CommonPref.clearPreferenceLogout(MainActivity.this);
                                        Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }).create().show();
                    }else{
                        Toast.makeText(MainActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    Toast.makeText(MainActivity.this, "No menu found", Toast.LENGTH_SHORT).show();
                    break;
            }

            return false;

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void callServiceForData() {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Hello Mr/Mrs" + userinfo.getName() + " please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Call<DashboardResponse> call1 = null;
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL).create(APIInterface.class);
        if (locationType.equals("HQR")) call1 = apiInterface.doGetDashboardAllData();
        else call1 = apiInterface.doGetDashboardAllData(locationType, loginLocation);
        call1.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if (dialog.isShowing()) dialog.dismiss();
                dashboardResponse = response.body();
                pending_vender = dashboardResponse.data.vendors.stream()
                        .filter(vendorPoso -> "INC".equals(vendorPoso.status) || "RCV".equals(vendorPoso.status))
                        .collect(Collectors.toList());
                verified_vendor = dashboardResponse.data.vendors.stream()
                        .filter(vendorPoso -> "CAL".equals(vendorPoso.status))
                        .collect(Collectors.toList());
                pending_manu = dashboardResponse.data.manufacturers.stream()
                        .filter(vendorPoso -> "INC".equals(vendorPoso.status) || "RCV".equals(vendorPoso.status))
                        .collect(Collectors.toList());
                verified_manu = dashboardResponse.data.manufacturers.stream()
                        .filter(vendorPoso -> "CAL".equals(vendorPoso.status))
                        .collect(Collectors.toList());
                expandableListDetail.clear();
                expandableListTitle.clear();
                expandableListDetail = new HashMap<String, List<String>>();
                List<String> trader = new ArrayList<String>();
                trader.add("Pending:" + pending_vender.size());
                trader.add("Varified:" + verified_vendor.size());
                trader.add("Rejected:0");
                expandableListDetail.put("Trader", trader);
                List<String> manufacturer = new ArrayList<String>();
                manufacturer.add("Pending:" + pending_manu.size());
                manufacturer.add("Varified:" + verified_manu.size());
                manufacturer.add("Rejected:0");
                expandableListDetail.put("Manufacturer", manufacturer);
                List<String> tools = new ArrayList<String>();
                tools.add("Scanner");
                expandableListDetail.put("Tools", tools);

                List<String> report = new ArrayList<String>();
                report.add("Market Inspection Details Entry");
                report.add("Revenue Details Entry");
                report.add("Renewal/Registration Entry");
                report.add("Payment Entry");
                expandableListDetail.put("Report", report);

                List<String> inspections = new ArrayList<String>();
                inspections.add("Inspection");
                expandableListDetail.put("Inspection", inspections);

                List<String> settings = new ArrayList<String>();
                settings.add("Profile");
                settings.add("Logout");
                expandableListDetail.put("Settings", settings);
                populateExpendableList();
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
                call.cancel();
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Really Exit ?")
                .setMessage("Are you sure want to exit ?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {
                    MainActivity.super.onBackPressed();
                    //finish();
                }).create().show();
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToscanCode();
                } else {
                    Toast.makeText(this, "Please Enable All permissions !", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void goToscanCode() {
        startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
    }
}