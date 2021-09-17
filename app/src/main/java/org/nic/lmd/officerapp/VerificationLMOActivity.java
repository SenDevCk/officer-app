package org.nic.lmd.officerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.nic.lmd.adapters.RenewalInstrumentAdapter;
import org.nic.lmd.adapters.RenewalWeightAdapter;
import org.nic.lmd.entities.StatusForRenewalData;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.InstrumentPoso;
import org.nic.lmd.retrofitPojo.VcPoso;
import org.nic.lmd.retrofitPojo.VendorDataResponse;
import org.nic.lmd.retrofitPojo.VendorPoso;
import org.nic.lmd.retrofitPojo.WeightPoso;
import org.nic.lmd.utilities.Urls_this_pro;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationLMOActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_weight, recycler_instrument;
    Button button_renew;
    Toolbar toolbar;
    RenewalWeightAdapter renewal_weight_adapter;
    RenewalInstrumentAdapter renewalInstrumentAdapter;
    TextView msg_weight, msg_instrument;
    APIInterface apiInterface;
    public static VendorDataResponse vendorDataResponse;
    public static ArrayList<StatusForRenewalData> statusForRenewalDataWeight = new ArrayList<>();
    public static ArrayList<StatusForRenewalData> statusForRenewalDataInstrument = new ArrayList<>();
    ImageView add_weight,add_instrument;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiction_lmo);
        toolbar = findViewById(R.id.toolbar_ren);
        toolbar.setTitle("Verification Details");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> VerificationLMOActivity.super.onBackPressed());
        msg_instrument = findViewById(R.id.msg_instrument);
        msg_weight = findViewById(R.id.msg_weight);
        button_renew = findViewById(R.id.button_renew);
        recycler_weight = findViewById(R.id.recycler_weight);
        recycler_instrument = findViewById(R.id.recycler_instrument);
        add_instrument = findViewById(R.id.add_instrument);
        add_weight = findViewById(R.id.add_weight);
        add_instrument.setOnClickListener(this);
        add_weight.setOnClickListener(this);
        statusForRenewalDataWeight.clear();
        statusForRenewalDataInstrument.clear();
        vendorDataResponse = null;
        progressDialog=new ProgressDialog(VerificationLMOActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        callServiceForData();
    }

    private void callServiceForData() {
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL).create(APIInterface.class);
        Call<VendorDataResponse> call1 = apiInterface.doGetVender(getIntent().getStringExtra("vid"));
        call1.enqueue(new Callback<VendorDataResponse>() {

            @Override
            public void onResponse(Call<VendorDataResponse> call, Response<VendorDataResponse> response) {
                if (progressDialog.isShowing())progressDialog.dismiss();
                vendorDataResponse = response.body();
                List<WeightPoso> weights=vendorDataResponse.vendorPoso.weights.stream()                // convert list to stream
                        .filter(weight -> (!"N".equals(weight.status)|| null==weight.status))
                        .collect(Collectors.toList());
                vendorDataResponse.vendorPoso.weights=null;
                vendorDataResponse.vendorPoso.weights= (ArrayList<WeightPoso>) weights;
                List<InstrumentPoso> instruments=vendorDataResponse.vendorPoso.instruments.stream()                // convert list to stream
                        .filter(instrument -> (!"N".equals(instrument.status)||null==instrument.status))
                        .collect(Collectors.toList());
                vendorDataResponse.vendorPoso.instruments=null;
                vendorDataResponse.vendorPoso.instruments= (ArrayList<InstrumentPoso>) instruments;
                String jsonObject = new Gson().toJson(vendorDataResponse);
                System.out.println(jsonObject);
                button_renew.setOnClickListener(VerificationLMOActivity.this);
                populateRecyclerView();
            }

            @Override
            public void onFailure(Call<VendorDataResponse> call, Throwable t) {
                Log.e("error",t.getMessage());
                if (progressDialog.isShowing())progressDialog.dismiss();
                call.cancel();
            }
        });
    }

    private void populateRecyclerView() {
        try {
            if (vendorDataResponse.vendorPoso.weights.size() > 0) {
                recycler_weight.invalidate();
                renewal_weight_adapter = new RenewalWeightAdapter(VerificationLMOActivity.this);
                RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(VerificationLMOActivity.this);
                recycler_weight.setLayoutManager(mLayoutManager2);
                recycler_weight.setItemAnimator(new DefaultItemAnimator());
                recycler_weight.setAdapter(renewal_weight_adapter);
            } else {
                msg_weight.append(" not avalable");
                msg_weight.setTextSize(12);
                msg_weight.setTextColor(getResources().getColor(R.color.design_default_color_error));
            }
            if (vendorDataResponse.vendorPoso.instruments.size() > 0) {
                recycler_instrument.invalidate();
                renewalInstrumentAdapter = new RenewalInstrumentAdapter(VerificationLMOActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VerificationLMOActivity.this);
                recycler_instrument.setLayoutManager(mLayoutManager);
                recycler_instrument.setItemAnimator(new DefaultItemAnimator());
                recycler_instrument.setAdapter(renewalInstrumentAdapter);
            } else {
                msg_instrument.append(" not avalable");
                msg_instrument.setTextSize(12);
                msg_instrument.setTextColor(getResources().getColor(R.color.design_default_color_error));
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg_weight.setText(e.getMessage());
            msg_weight.setTextSize(12);
            msg_weight.setTextColor(getResources().getColor(R.color.design_default_color_error));
            msg_instrument.setVisibility(View.GONE);
            button_renew.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(VerificationLMOActivity.this,AddMoreActivity.class);
        if (v.getId()==R.id.button_renew) {
            new AlertDialog.Builder(VerificationLMOActivity.this)
                    .setTitle("Verify")
                    .setMessage("Really want to verify")
                    .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        //Log.d("before", jsonObject.toString());
                        int max_vc_id = 0;
                        String ven_id = null;
                        ArrayList<VcPoso> jsonArray_vc = vendorDataResponse.vendorPoso.vcs;
                        for (VcPoso vc : jsonArray_vc) {
                            if (max_vc_id < vc.vcId) {
                                max_vc_id = vc.vcId;
                                ven_id = vc.vendorId;
                            }
                        }
                        VcPoso new_vc = new VcPoso();
                        new_vc.nextVcDate = null;
                        new_vc.vcDate = null;
                        new_vc.vcId = max_vc_id + 1;
                        new_vc.vcNumber = null;
                        new_vc.vendorId = ven_id;
                        vendorDataResponse.vendorPoso.vcs.add(new_vc);
                        manipulateDataForWeightAndInstrument(true, max_vc_id);
                        manipulateDataForWeightAndInstrument(false, max_vc_id);
                        Log.d("json_manipulated", new Gson().toJson(vendorDataResponse.vendorPoso));
                        //call_service here
                        //new UploadTradorService().execute()
                        //populateRecyclerView();
                    }).create().show();
        }else if (v.getId()==R.id.add_weight){
            intent.putExtra("from","weight");
            startActivity(intent);
        }else if (v.getId()==R.id.add_instrument){
            intent.putExtra("from","instrument");
            startActivity(intent);
        }
    }

    private void manipulateDataForWeightAndInstrument(boolean flag, int max_vc_id) {
        try {
            ArrayList<?> jsonArray_man = (flag) ? vendorDataResponse.vendorPoso.weights : vendorDataResponse.vendorPoso.instruments;
            for (int i = 0; i < jsonArray_man.size(); i++) {
                Object jsonObject_man = jsonArray_man.get(i);
                if (flag) {
                    WeightPoso weight = (WeightPoso) jsonArray_man.get(i);
                    if (weight.status == null) {
                        vendorDataResponse.vendorPoso.weights.get(i).status = "E";
                    } else {
                        if (statusForRenewalDataWeight.get(i).isRenewed() && vendorDataResponse.vendorPoso.weights.get(i).status.equals("D")) {
                          WeightPoso new_man = vendorDataResponse.vendorPoso.weights.get(i);
                            new_man.status = "A";
                            new_man.vcId = max_vc_id + 1;
                            vendorDataResponse.vendorPoso.weights.get(i).status = "E";
                            vendorDataResponse.vendorPoso.weights.add(new_man);
                        } else if (statusForRenewalDataWeight.get(i).isDeleted()) {
                            vendorDataResponse.vendorPoso.weights.get(i).status = "E";
                        }
                    }
                } else {
                   InstrumentPoso instrument = (InstrumentPoso) jsonArray_man.get(i);
                    if (instrument.status == null) {
                        vendorDataResponse.vendorPoso.instruments.get(i).status = "E";
                    } else {
                        if (statusForRenewalDataInstrument.get(i).isRenewed() && vendorDataResponse.vendorPoso.weights.get(i).status.equals("D")) {
                            InstrumentPoso new_man = vendorDataResponse.vendorPoso.instruments.get(i);
                            new_man.status = "A";
                            new_man.vcId = max_vc_id + 1;
                            vendorDataResponse.vendorPoso.instruments.get(i).status = "E";
                            vendorDataResponse.vendorPoso.instruments.add(new_man);
                        } else if (statusForRenewalDataInstrument.get(i).isDeleted()) {
                            vendorDataResponse.vendorPoso.instruments.get(i).status = "E";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}