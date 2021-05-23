package org.nic.lmd.officerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nic.lmd.adapters.BillingDetailsAdapter;
import org.nic.lmd.adapters.DocumentRecyclerAdapter;
import org.nic.lmd.adapters.ManufacturerPatnerAdapter;
import org.nic.lmd.adapters.ManufacturerWeightAdapter;
import org.nic.lmd.adapters.ManufacurerDocRecyclerAdapter;
import org.nic.lmd.adapters.ManufacurerInstrumentAdapter;
import org.nic.lmd.adapters.PatnerRecyclerAdapter;
import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.PatnerEntity;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.InstrumentPoso;
import org.nic.lmd.retrofitPojo.ManufacturerDataResponse;
import org.nic.lmd.retrofitPojo.ManufacturerPoso;
import org.nic.lmd.retrofitPojo.VendorDataResponse;
import org.nic.lmd.retrofitPojo.WeightPoso;
import org.nic.lmd.services.WebServiceHelper;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.Utiilties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManufactureFeeCalculationActivity extends AppCompatActivity {

    String place = "";
    Toolbar toolbar;
    RecyclerView list_den_rec,list_ins_rec, list_partner_rec, list_docs;
    ManufacturerWeightAdapter manufacturerWeightAdapter;
    ManufacurerInstrumentAdapter manufacurerInstrumentAdapter;
    ManufacurerDocRecyclerAdapter documentRecyclerAdapter;
    ManufacturerPatnerAdapter patnerRecyclerAdapter;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    TextView tv_com_date, tv_vari_date, tv_vid, tv_name, tv_add_ven, tv_pre_type;
    ImageView bt_com_date, bt_var_date;
    TextView text_total_vf, text_total_af, text_total_ur, text_total_cc, text_total_co, text_grand_total;
    private boolean check = false;
    int i = 0, ch_date_pic = 0;
    String text_previ_com_date, date_change;
    TextView text_no_doc;
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    ManufacturerPoso manufacturerResData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacture_fee_calculation);
        toolbar = findViewById(R.id.toolbar_bill_manufac);
        String from=(getIntent().hasExtra("from"))?getIntent().getStringExtra("from"):"";
        if (from.equals("scan")){
            toolbar.setTitle("Manufacture Details");
            toolbar.setSubtitle(getIntent().getStringExtra("man_id").trim());
        }else{
            toolbar.setTitle("Verification Fee");
            toolbar.setSubtitle(getResources().getString(R.string.app_name));
        }
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManufactureFeeCalculationActivity.super.onBackPressed();
            }
        });

        list_partner_rec = findViewById(R.id.list_partner_rec);
        list_den_rec = findViewById(R.id.list_den_rec);
        list_ins_rec = findViewById(R.id.list_ins_rec);
        list_docs = findViewById(R.id.list_docs);
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
                     //jsonArray = jsonObject.getJSONArray("vcs");
                    //JSONObject jsonObject_vc = jsonArray.getJSONObject(0);
                    //jsonObject_vc.remove("vcDate");
                    //jsonObject_vc.accumulate("vcDate", tv_vari_date.getText().toString().trim());
                    //jsonArray.put(0, jsonObject_vc);
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
                    //manufacturerResData.manufacturerPoso.co
                    //jsonObject.remove("commencementDate");
                    //jsonObject.accumulate("commencementDate", date_change);
                    callService();
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        bt_com_date = findViewById(R.id.bt_com_date);
      /* bt_com_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch_date_pic = 0;
                ShowDialog2();
            }
        });*/
        bt_var_date = findViewById(R.id.bt_var_date);
       /* bt_var_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ch_date_pic = 1;
                ShowDialog2();
            }
        });*/
        progressDialog=new ProgressDialog(ManufactureFeeCalculationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        callService();
    }


    private void ShowDialog2() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datedialog = new DatePickerDialog(ManufactureFeeCalculationActivity.this,
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

    private void callService() {
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL).create(APIInterface.class);
        Call<ManufacturerPoso> call1 = apiInterface.doGetManufacture((getIntent().hasExtra("man_id"))?getIntent().getStringExtra("man_id").trim():"");
        call1.enqueue(new Callback<ManufacturerPoso>() {
            @Override
            public void onResponse(Call<ManufacturerPoso> call, Response<ManufacturerPoso> response) {
                if (progressDialog.isShowing())progressDialog.dismiss();
                manufacturerResData = response.body();
                //tv_vari_date.setText(manufacturerResData.);
                tv_vid.setText(""+manufacturerResData.manufacturerId);
                tv_name.setText(""+manufacturerResData.name);
                tv_pre_type.setText(""+new DataBaseHelper(ManufactureFeeCalculationActivity.this).getPremissesByID(String.valueOf(manufacturerResData.premisesType)).getName());
                tv_add_ven.setText(""+manufacturerResData.address1+"\n"+manufacturerResData.address2);
                populateRecyclerView();
            }

            @Override
            public void onFailure(Call<ManufacturerPoso> call, Throwable t) {
                Log.e("error",t.getMessage());
                call.cancel();
                if (progressDialog.isShowing())progressDialog.dismiss();
                Toast.makeText(ManufactureFeeCalculationActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void CallServiceForSubmit(View view) {
        if (tv_vari_date.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Select Date of Varification !", Toast.LENGTH_SHORT).show();
        } else if (place.equals("OWNER OFFICE") && text_total_cc.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter CC Amount !", Toast.LENGTH_SHORT).show();
        } else if (place.equals("OWNER OFFICE") && Double.parseDouble(text_total_cc.getText().toString().trim()) <= 0) {
            Toast.makeText(this, "Enter Valid CC Amount !", Toast.LENGTH_SHORT).show();
        } else if (!Utiilties.isOnline(ManufactureFeeCalculationActivity.this)) {
            Toast.makeText(this, "Internet Not Avalable !", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please go online !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Work in progress !", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateRecyclerView() {

           list_partner_rec.invalidate();
        if (manufacturerResData.officials.size()>0) {
            patnerRecyclerAdapter = new ManufacturerPatnerAdapter(ManufactureFeeCalculationActivity.this, manufacturerResData.officials);
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(ManufactureFeeCalculationActivity.this);
            list_partner_rec.setLayoutManager(mLayoutManager1);
            list_partner_rec.setItemAnimator(new DefaultItemAnimator());
            list_partner_rec.setAdapter(patnerRecyclerAdapter);
        }

            list_den_rec.invalidate();
        if (manufacturerResData.weights.size()>0) {
            manufacturerWeightAdapter = new ManufacturerWeightAdapter(ManufactureFeeCalculationActivity.this, manufacturerResData.weights);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ManufactureFeeCalculationActivity.this);
            list_den_rec.setLayoutManager(mLayoutManager);
            list_den_rec.setItemAnimator(new DefaultItemAnimator());
            list_den_rec.setAdapter(manufacturerWeightAdapter);
        }

           list_ins_rec.invalidate();
        if (manufacturerResData.instruments.size()>0) {
            manufacurerInstrumentAdapter = new ManufacurerInstrumentAdapter(ManufactureFeeCalculationActivity.this, manufacturerResData.instruments);
            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(ManufactureFeeCalculationActivity.this);
            list_ins_rec.setLayoutManager(mLayoutManager2);
            list_ins_rec.setItemAnimator(new DefaultItemAnimator());
            list_ins_rec.setAdapter(manufacurerInstrumentAdapter);
        }

           list_docs.invalidate();
        if (manufacturerResData.docs.size()>0) {
            documentRecyclerAdapter = new ManufacurerDocRecyclerAdapter(manufacturerResData.docs, ManufactureFeeCalculationActivity.this);
            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(ManufactureFeeCalculationActivity.this);
            list_docs.setLayoutManager(mLayoutManager3);
            list_docs.setItemAnimator(new DefaultItemAnimator());
            list_docs.setAdapter(documentRecyclerAdapter);
        }

    }
}