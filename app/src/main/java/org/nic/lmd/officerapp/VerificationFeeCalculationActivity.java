package org.nic.lmd.officerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nic.lmd.adapters.BillingDetailsAdapter;
import org.nic.lmd.adapters.DocumentRecyclerAdapter;
import org.nic.lmd.adapters.PatnerRecyclerAdapter;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.PatnerEntity;
import org.nic.lmd.interfaces.VenderListListener;
import org.nic.lmd.interfaces.VendorDataListener;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.services.DocumentListLoader;
import org.nic.lmd.services.VenderDataForBillingLoader;
import org.nic.lmd.services.VenderDataForBillingUpdateLoader;
import org.nic.lmd.services.WebServiceHelper;
import org.nic.lmd.utilities.Utiilties;


import java.util.ArrayList;
import java.util.Calendar;

public class VerificationFeeCalculationActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    RadioGroup rd_gp_place;
    String place = "";
    RadioButton radioButton;
    JSONObject jsonObject;
    RecyclerView recyclerView, list_partner_rec, list_docs;
    BillingDetailsAdapter billingDetailsAdapter;
    DocumentRecyclerAdapter documentRecyclerAdapter;
    PatnerRecyclerAdapter patnerRecyclerAdapter;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    TextView tv_com_date,tv_vari_date,tv_vid,tv_name,tv_add_ven,tv_pre_type;
    ImageView bt_com_date, bt_var_date;
    TextView text_total_vf, text_total_af, text_total_ur, text_total_cc, text_total_co, text_grand_total;
    private boolean check = false;
    int i = 0, ch_date_pic = 0;
    String text_previ_com_date, date_change;
    TextView text_no_doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_var_fee_calculation);
        toolbar = findViewById(R.id.toolbar_bill);
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> VerificationFeeCalculationActivity.super.onBackPressed());
        rd_gp_place = findViewById(R.id.rd_gp_place);
        rd_gp_place.setOnCheckedChangeListener((group, checkedId) -> {
            // find which radio button is selected
            if (checkedId == R.id.radio1) {
                place = "OWNER OFFICE";
            } else {
                place = "LMO OFFICE";
            }
        });

        list_docs = findViewById(R.id.list_docs);
        list_partner_rec = findViewById(R.id.list_partner_rec);
        recyclerView = findViewById(R.id.list_denomination_bill);
        text_no_doc = findViewById(R.id.text_no_doc);

        tv_vid = findViewById(R.id.tv_vid);
        tv_name = findViewById(R.id.tv_name);
        tv_pre_type = findViewById(R.id.tv_pre_type);
        tv_add_ven = findViewById(R.id.tv_add_ven);

        text_total_vf = findViewById(R.id.text_total_vf);
        text_total_af = findViewById(R.id.text_total_af);
        text_total_ur = findViewById(R.id.text_total_ur);
        text_total_cc = findViewById(R.id.text_total_cc);
        text_total_co = findViewById(R.id.text_total_co);
        text_grand_total = findViewById(R.id.text_grand_total);

        tv_com_date = findViewById(R.id.tv_com_date);
        tv_vari_date = findViewById(R.id.tv_vari_date);

        tv_vari_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = jsonObject.getJSONArray("vcs");
                    JSONObject jsonObject_vc = jsonArray.getJSONObject(0);
                    jsonObject_vc.remove("vcDate");
                    jsonObject_vc.accumulate("vcDate", tv_vari_date.getText().toString().trim());
                    jsonArray.put(0, jsonObject_vc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        tv_com_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                date_change = editable.toString();
                try {
                    check = true;
                    jsonObject.remove("commencementDate");
                    jsonObject.accumulate("commencementDate", date_change);
                    callService();
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bt_com_date =  findViewById(R.id.bt_com_date);
        bt_com_date.setOnClickListener(view -> {
            ch_date_pic = 0;
            ShowDialog2();
        });
        bt_var_date =  findViewById(R.id.bt_var_date);
        bt_var_date.setOnClickListener(view -> {
            ch_date_pic = 1;
            ShowDialog2();
        });
        if (getIntent().hasExtra("from")){
            String from=getIntent().getStringExtra("from");
            if (from.equals("scan")) toolbar.setTitle("Vendor Details");
        }
        callService();
    }

    private void callService() {
        VenderDataForBillingLoader.listenVendor(new VendorDataListener() {
            @Override
            public void responseFound(String res) {
                try {
                    Log.d("response",res);
                    jsonObject = new JSONObject(res);
                    //place=jsonObject.getString("placeOfverification");
                    if (place.equalsIgnoreCase("OWNER OFFICE")) {
                        radioButton = (RadioButton) rd_gp_place.findViewById(R.id.radio1);
                        radioButton.setChecked(true);
                    } else {
                        radioButton = (RadioButton) rd_gp_place.findViewById(R.id.radio2);
                        radioButton.setChecked(true);
                    }
                    if (!jsonObject.isNull("natureOfBusiness"))
                    tv_pre_type.setText(""+new DataBaseHelper(VerificationFeeCalculationActivity.this).getNatureofBusinessByID(jsonObject.getString("natureOfBusiness").trim()).getValue());
                    if (!jsonObject.isNull("vendorId"))
                    tv_vid.setText(""+jsonObject.getString("vendorId"));
                    tv_name.setText(""+jsonObject.getString("nameOfBusinessShop"));
                    tv_add_ven.setText(""+jsonObject.getString("address1")+"\n"+jsonObject.getString("address2"));

                    if (!jsonObject.isNull("instrumentVFTotal")) {
                        tv_com_date.setText("" + jsonObject.getString("commencementDate"));
                        text_total_vf.setText(String.valueOf(Double.parseDouble(jsonObject.getString("instrumentVFTotal")) + Double.parseDouble(jsonObject.getString("weightVFTotal"))));
                        text_total_af.setText(String.valueOf(Double.parseDouble(jsonObject.getString("instrumentAFTotal")) + Double.parseDouble(jsonObject.getString("weightAFTotal"))));
                        text_total_ur.setText(String.valueOf(Double.parseDouble(jsonObject.getString("instrumentURTotal")) + Double.parseDouble(jsonObject.getString("weightURTotal"))));
                        text_total_cc.setText("0.0");
                        text_total_co.setText("0.0");
                        text_grand_total.setText("" + jsonObject.getString("grandTotal"));
                    }
                    populateRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callServiceforDocument();
            }

            @Override
            public void responseNotFound(String msg) {
                tv_com_date.setText("" + text_previ_com_date);
            }
        });

        if (!Utiilties.isOnline(VerificationFeeCalculationActivity.this)) {
            Toast.makeText(this, "Internet Not Avalable !", Toast.LENGTH_SHORT).show();
        } else {
            if (check == false && i == 0) {
                new VenderDataForBillingLoader(VerificationFeeCalculationActivity.this).execute(getIntent().getStringExtra("vid"));
            }
            else if (check == true && i > 1) {
                new VenderDataForBillingLoader(VerificationFeeCalculationActivity.this, jsonObject).execute(getIntent().getStringExtra("vid"));
            }
        }
    }

    private void ShowDialog2() {

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(VerificationFeeCalculationActivity.this,
                mDateSetListener1, mYear, mMonth, mDay);
        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            try {
                text_previ_com_date = tv_com_date.getText().toString().trim();
                if (mDay < 10 && (mMonth + 1) > 9) {
                    mDay = Integer.parseInt("0" + mDay);
                    if (ch_date_pic == 0)
                        tv_com_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append("0" + mDay));
                    else
                        tv_vari_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append("0" + mDay));
                } else if ((mMonth + 1) < 10 && mDay > 9) {
                    mMonth = Integer.parseInt("0" + mMonth);
                    if (ch_date_pic == 0)
                        tv_com_date.setText(new StringBuilder().append(mYear).append("-").append("0" + (mMonth + 1)).append("-").append(mDay));
                    else
                        tv_vari_date.setText(new StringBuilder().append(mYear).append("-").append("0" + (mMonth + 1)).append("-").append(mDay));
                } else if ((mMonth + 1) < 10 && mDay < 10) {
                    mDay = Integer.parseInt("0" + mDay);
                    mMonth = Integer.parseInt("0" + mMonth);
                    if (ch_date_pic == 0)
                        tv_com_date.setText(new StringBuilder().append(mYear).append("-").append("0" + (mMonth + 1)).append("-").append("0" + mDay));
                    else
                        tv_vari_date.setText(new StringBuilder().append(mYear).append("-").append("0" + (mMonth + 1)).append("-").append("0" + mDay));
                } else {
                    if (ch_date_pic == 0)
                        tv_com_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
                    else
                        tv_vari_date.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void populateRecyclerView() {
        list_partner_rec.invalidate();
        ArrayList<PatnerEntity> patnerEntities = WebServiceHelper.parsePatner(jsonObject.toString(), VerificationFeeCalculationActivity.this);
        patnerRecyclerAdapter = new PatnerRecyclerAdapter(patnerEntities, VerificationFeeCalculationActivity.this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(VerificationFeeCalculationActivity.this);
        list_partner_rec.setLayoutManager(mLayoutManager1);
        list_partner_rec.setItemAnimator(new DefaultItemAnimator());
        list_partner_rec.setAdapter(patnerRecyclerAdapter);

        recyclerView.invalidate();
        billingDetailsAdapter = new BillingDetailsAdapter(jsonObject, VerificationFeeCalculationActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VerificationFeeCalculationActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(billingDetailsAdapter);
    }


    public void CallServiceForSubmit(View view) {
        if (tv_vari_date.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Select Date of Varification !", Toast.LENGTH_SHORT).show();
        } else if (place.equals("OWNER OFFICE") && text_total_cc.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter CC Amount !", Toast.LENGTH_SHORT).show();
        } else if (place.equals("OWNER OFFICE") && Double.parseDouble(text_total_cc.getText().toString().trim()) <= 0) {
            Toast.makeText(this, "Enter Valid CC Amount !", Toast.LENGTH_SHORT).show();
        } else if (!Utiilties.isOnline(VerificationFeeCalculationActivity.this)) {
            Toast.makeText(this, "Internet Not Avalable !", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please go online !", Toast.LENGTH_SHORT).show();
        } else {
            try {
                JSONObject jsonObject_recept = new JSONObject();
                jsonObject_recept.accumulate("vendorId", jsonObject.getString("vendorId"));
                jsonObject_recept.accumulate("verificationFee", Float.parseFloat(text_total_vf.getText().toString().trim()));
                jsonObject_recept.accumulate("underRuleFee", Float.parseFloat(text_total_ur.getText().toString().trim()));
                jsonObject_recept.accumulate("additionalFee", Float.parseFloat(text_total_af.getText().toString().trim()));
                jsonObject_recept.accumulate("convenienceCharge", Float.parseFloat(text_total_cc.getText().toString().trim()));
                jsonObject_recept.accumulate("vcId", Integer.parseInt((jsonObject.getJSONArray("vcs").getJSONObject(0)).getString("vcId")));
                jsonObject_recept.accumulate("inspector", CommonPref.getUserDetails(VerificationFeeCalculationActivity.this).getUserid());
                JSONArray rec_jarray = jsonObject.getJSONArray("receipts");
                rec_jarray.put(jsonObject_recept);
                jsonObject.remove("receipts");
                jsonObject.accumulate("receipts", rec_jarray);
                VenderDataForBillingUpdateLoader.listenVendorUpdate(new VendorDataListener() {
                    @Override
                    public void responseFound(String res) {
                        //Log.d("log", "" + res);
                        Toast.makeText(VerificationFeeCalculationActivity.this, "" + res, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(2, intent);
                        finish();
                    }

                    @Override
                    public void responseNotFound(String msg) {
                        Log.e("log", "" + msg);
                        Toast.makeText(VerificationFeeCalculationActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                    }
                });
                new VenderDataForBillingUpdateLoader(VerificationFeeCalculationActivity.this, jsonObject).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void callServiceforDocument() {
        DocumentListLoader.listenVendorList(new VenderListListener() {
            @Override
            public void responseFound(String res) {
                try {
                    JSONArray jsonArray = new JSONArray(res);
                    if (jsonArray.length() > 0) {
                        text_no_doc.setText("");
                        list_docs.invalidate();
                        documentRecyclerAdapter = new DocumentRecyclerAdapter(jsonArray, VerificationFeeCalculationActivity.this);
                        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(VerificationFeeCalculationActivity.this);
                        list_docs.setLayoutManager(mLayoutManager3);
                        list_docs.setItemAnimator(new DefaultItemAnimator());
                        list_docs.setAdapter(documentRecyclerAdapter);
                    }else{
                        text_no_doc.setText("No Document found !");
                    }
                } catch (Exception e) {
                    Log.e("json-error", e.getMessage());
                }

            }

            @Override
            public void responseNotFound(String msg) {
                Toast.makeText(VerificationFeeCalculationActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
            }
        });
        try {
            new DocumentListLoader(VerificationFeeCalculationActivity.this).execute(jsonObject.getString("vendorId"));
        } catch (Exception e) {
            Log.e("json-error", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {

    }
}