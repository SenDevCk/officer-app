package org.nic.lmd.officerapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.nic.lmd.databases.DataBaseHelper;
import org.nic.lmd.entities.AdalatCaseDetails;
import org.nic.lmd.entities.AdalatConsumerDetail;
import org.nic.lmd.entities.AdalatPayDetails;
import org.nic.lmd.entities.Block;
import org.nic.lmd.entities.District;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.retrofit.APIClient;
import org.nic.lmd.retrofit.APIInterface;
import org.nic.lmd.retrofitPojo.MyResponse;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.Utiilties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentEntryActivity extends AppCompatActivity {
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Spinner sp_district, sp_block, sp_paymode, sp_con_type,sp_adalat;
    Block block;
    District district;
    String payMode = "", typeOfAdalat = "", customerType = "";
    EditText edit_pay_amt, edit_case_no, edit_amount_com, edit_court_name,edit_grn;
    EditText edit_com_id,edit_consumer_name,edit_consumer_add;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    TextView text_total_amt, date_sel ,text_con_count ;
    Button button_add, button_final_submit;
    TextInputLayout text_input_com_amt, text_input_case_no, text_input_court_name,text_input_grn,text_input_amt_tot;
    TextInputLayout text_input_con_id,text_input_con_name,text_input_con_add;
    ArrayList<AdalatCaseDetails> cases = new ArrayList<>();
    LinearLayout ll_pay,ll_com_consumer;
    CardView card_pay;
    ScrollView scroll_pay;
    TextView text_date,text_court,text_grn,text_amt,text_mode,text_adalat;
    Integer subDiv;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_entry);
        subDiv=Integer.parseInt(getIntent().getStringExtra("subDiv"));
        toolbar = findViewById(R.id.toolbar_ap_new);
        toolbar.setTitle("Payment Activity");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> PaymentEntryActivity.super.onBackPressed());
        scroll_pay=findViewById(R.id.scroll_pay);
        card_pay=findViewById(R.id.card_pay);
        text_con_count=findViewById(R.id.text_con_count);
        sp_district = findViewById(R.id.sp_district);
        sp_block = findViewById(R.id.sp_block);
        edit_court_name = findViewById(R.id.edit_court_name);
        sp_adalat = findViewById(R.id.sp_adalat);
        sp_paymode = findViewById(R.id.sp_paymode);
        sp_con_type = findViewById(R.id.sp_con_type);
        date_sel = findViewById(R.id.date_sel);
        text_total_amt = findViewById(R.id.text_total_amt);
        ll_com_consumer = findViewById(R.id.ll_com_consumer);
        ll_pay = findViewById(R.id.ll_pay);
        card_pay.setVisibility(View.GONE);
        ll_com_consumer.setVisibility(View.GONE);
        edit_amount_com = findViewById(R.id.edit_amount_com);
        edit_grn = findViewById(R.id.edit_grn);
        edit_case_no = findViewById(R.id.edit_case_no);
        edit_pay_amt = findViewById(R.id.edit_pay_amt);
        edit_com_id = findViewById(R.id.edit_com_id);
        edit_consumer_name = findViewById(R.id.edit_consumer_name);
        edit_consumer_add = findViewById(R.id.edit_consumer_add);

        text_date = findViewById(R.id.text_date);
        text_court = findViewById(R.id.text_court);
        text_grn = findViewById(R.id.text_grn);
        text_amt = findViewById(R.id.text_amt);
        text_mode = findViewById(R.id.text_mode);
        text_adalat = findViewById(R.id.text_adalat);

        text_input_con_name = findViewById(R.id.text_input_con_name);
        text_input_con_add = findViewById(R.id.text_input_con_add);
        text_input_con_id = findViewById(R.id.text_input_con_id);
        text_input_case_no = findViewById(R.id.text_input_case_no);
        text_input_com_amt = findViewById(R.id.text_input_com_amt);
        button_final_submit = findViewById(R.id.button_final_submit);
        text_input_court_name = findViewById(R.id.text_input_court_name);
        text_input_grn = findViewById(R.id.text_input_grn);
        text_input_amt_tot = findViewById(R.id.text_input_amt_tot);
        date_sel.setOnClickListener(v -> {
            ShowDialog();
        });

        button_final_submit.setOnClickListener(v -> {
            finalSubmit();
        });
        button_add = findViewById(R.id.button_add);
        button_add.setOnClickListener(v -> {
            addAmount();
        });

        populateDistrictSpinner();
        populateBlockSpinner("0");
        populatePaymentModeSpiner();
        populateCustomerSpiner();
        populateTypeOfAdalatSpiner();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.pay_details_menu, menu);
        (menu.findItem(R.id.done)).setVisible(false);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_case: {
                //AlertDialogForPrinter();
                if (cases.size()>0) {
                    Intent intent = new Intent(PaymentEntryActivity.this, EditCaseActivity.class);
                    intent.putExtra("cases", cases);
                    startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(PaymentEntryActivity.this, "No Cases", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode == RESULT_OK) {
                cases=null;
               cases= (ArrayList<AdalatCaseDetails>) data.getSerializableExtra("result");
               Double ctotal=Double.parseDouble(text_amt.getText().toString().trim());
               if (cases.size()>0) {
                   Double sum = cases.stream()
                           .mapToDouble(x -> x.getCompounding_amt())
                           .sum();
                   text_total_amt.setText("" + (ctotal - sum));
                   text_con_count.setText(""+cases.size());
               }else{
                   text_total_amt.setText(text_amt.getText().toString().trim());
                   text_con_count.setText(""+cases.size());
               }
            }else{
                Toast.makeText(PaymentEntryActivity.this, "Data not Edited", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void finalSubmit() {
        UserData userData= CommonPref.getUserDetails(PaymentEntryActivity.this);
            AdalatPayDetails adalatPayDetails= AdalatPayDetails.builder()
                    .payment_mode(payMode)
                    .grn_number(text_grn.getText().toString().trim())
                    .amount(Double.parseDouble(text_amt.getText().toString().trim()))
                    .amount_adjust(0.0)
                    .type_of_adalat(text_adalat.getText().toString().trim())
                    .date_of_adalat(text_date.getText().toString().trim())
                    .name_of_court(text_court.getText().toString().trim())
                    .user_id(userData.getUserid())
                    .sub_div_id(subDiv)
                    .cases(cases)
                    .build();
            new AlertDialog.Builder(PaymentEntryActivity.this)
                    .setTitle("Really Upload Data")
                    .setMessage("Are you sure you want to Upload Data")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {
                                uploadPaymetDetails(adalatPayDetails);
                    }).create().show();

    }

    private void uploadPaymetDetails(AdalatPayDetails adalatPayDetails){
        System.out.println(adalatPayDetails);
        progressDialog = new ProgressDialog(PaymentEntryActivity.this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL3).create(APIInterface.class);
        Call<MyResponse<AdalatPayDetails>> call1 = apiInterface.uploadingAdalatDetails(adalatPayDetails);
        call1.enqueue(new Callback<MyResponse<AdalatPayDetails>>() {
            @Override
            public void onResponse(Call<MyResponse<AdalatPayDetails>> call, Response<MyResponse<AdalatPayDetails>> response) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                MyResponse<AdalatPayDetails> myResponse = null;
                if (response.body() != null) myResponse = response.body();
                if (myResponse == null) {
                    Toast.makeText(PaymentEntryActivity.this, "Null Response found !", Toast.LENGTH_SHORT).show();
                } else if (myResponse.getStatusCode() == 200) {
                    Toast.makeText(PaymentEntryActivity.this, "Data Successfully Sent", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PaymentEntryActivity.this, "" + myResponse.getRemarks(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyResponse<AdalatPayDetails>> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(PaymentEntryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) progressDialog.dismiss();
                call.cancel();
            }
        });

    }


     private void addAmount() {
        if (edit_case_no.getText().toString().trim().equals("")) {
            text_input_case_no.setError("Required");
            Utiilties.scrollToView(scroll_pay, text_input_case_no);
        } else if (edit_amount_com.getText().toString().trim().equals("") || edit_amount_com.getText().toString().trim().equals("0")) {
            text_input_com_amt.setError("Required");
            Utiilties.scrollToView(scroll_pay, text_input_com_amt);
        }
        else if (customerType.equals("")){
            Toast.makeText(PaymentEntryActivity.this, "Please Select Customer Type !", Toast.LENGTH_SHORT).show();
        }
       /* else if (edit_com_id.getText().toString().trim().length()<3){
            text_input_con_id.setError("valid Consumer Id");
            Utiilties.scrollToView(scroll_pay, text_input_con_id);
        }*/
        else if (edit_consumer_name.getText().toString().trim().length()<3){
            text_input_con_name.setError("valid Consumer Id");
            Utiilties.scrollToView(scroll_pay, edit_consumer_name);
        }
        else if (edit_consumer_add.getText().toString().trim().length()<3){
            text_input_con_add.setError("valid Consumer Address");
            Utiilties.scrollToView(scroll_pay, edit_consumer_add);
        }
        else if (district==null){
            Toast.makeText(PaymentEntryActivity.this, "Please Select District !", Toast.LENGTH_SHORT).show();
            Utiilties.scrollToView(scroll_pay, sp_district);
        }
        else if (block==null){
            Toast.makeText(PaymentEntryActivity.this, "Please Select Block !", Toast.LENGTH_SHORT).show();
            Utiilties.scrollToView(scroll_pay, sp_block);
        }
        else {
            if ((Double.parseDouble(text_total_amt.getText().toString().trim()) - Double.parseDouble(edit_amount_com.getText().toString().trim()))>=0) {
                ArrayList<AdalatConsumerDetail> cons = new ArrayList<AdalatConsumerDetail>();
                AdalatConsumerDetail consumer = AdalatConsumerDetail.builder()
                        .district_id(new Integer(district.getId()))
                        .block_id(new Integer(block.getValue()))
                        .type_of_consumer(customerType)
                        .consumer_id(edit_com_id.getText().toString().trim().equals("")?null:edit_com_id.getText().toString().trim())
                        .consumer_address(edit_consumer_add.getText().toString().trim())
                        .consumer_name(edit_consumer_add.getText().toString().trim())
                        .build();
                cons.add(consumer);
                cases.add(AdalatCaseDetails.builder().case_number(edit_case_no.getText().toString().trim()).
                        compounding_amt(Double.parseDouble(edit_amount_com.getText().toString().trim()))
                        .consumers(cons)
                        .build());
                text_total_amt.setText("" + (Double.parseDouble(text_total_amt.getText().toString().trim()) - Double.parseDouble(edit_amount_com.getText().toString().trim())));
                text_con_count.setText("" + cases.size());
                edit_case_no.setText("");
                edit_amount_com.setText("");
                Utiilties.scrollToView(scroll_pay, ll_pay);
                Toast.makeText(PaymentEntryActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                removeAllErrors();
            }else{
                Toast.makeText(PaymentEntryActivity.this,"Amount to large",Toast.LENGTH_SHORT).show();
                text_input_com_amt.setError("Amount to large");
            }
            if (Double.parseDouble(text_total_amt.getText().toString().trim())<=0){
                button_final_submit.setVisibility(View.VISIBLE);
                button_add.setVisibility(View.GONE);
            }
        }
    }

    private void removeAllErrors() {
        edit_case_no.setError(null);
        edit_amount_com.setText(null);
        edit_com_id.setText(null);
        edit_consumer_add.setText(null);
        edit_consumer_add.setText(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateDistrictSpinner() {
        ArrayList<String> array_dist = new ArrayList<>();
        array_dist.add("--Select District--");
        ArrayList<District> districts = new DataBaseHelper(PaymentEntryActivity.this).getDistrict();
        districts.forEach(item -> {
            //Log.d("log",item.toString());
            array_dist.add(item.getName());
        });
        //Log.d("log",array_dist.toString());
        sp_district.setAdapter(new ArrayAdapter<>(PaymentEntryActivity.this, android.R.layout.simple_list_item_1, array_dist));
        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    district = districts.get(position);
                    populateBlockSpinner(district.getId());
                } else {
                    district = null;
                    populateBlockSpinner("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateBlockSpinner(final String districtId) {
        ArrayList<String> array_block = new ArrayList<>();
        array_block.add("--Select Block--");
        ArrayList<Block> blocks = new DataBaseHelper(PaymentEntryActivity.this).getBlock(districtId);
        blocks.forEach(item -> {
            array_block.add(item.getName());
        });
        sp_block.setAdapter(new ArrayAdapter<>(PaymentEntryActivity.this, android.R.layout.simple_list_item_1, array_block));
        sp_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    block = blocks.get(position + 1);
                } else {
                    block = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populatePaymentModeSpiner() {
        List<String> list_pay_mode = Arrays.asList(new String[]{"--Select Mode--", "Online", "Challan"});
        sp_paymode.setAdapter(new ArrayAdapter<>(PaymentEntryActivity.this, android.R.layout.simple_list_item_1, list_pay_mode));
        sp_paymode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    payMode = (String) ((ArrayAdapter) parent.getAdapter()).getItem(position);
                } else {
                    payMode = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateTypeOfAdalatSpiner() {
        List<String> list_pay_mode = Arrays.asList(new String[]{"--Select Mode--", "Lok-Adalat", "Other"});
        sp_adalat.setAdapter(new ArrayAdapter<>(PaymentEntryActivity.this, android.R.layout.simple_list_item_1, list_pay_mode));
        sp_adalat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    typeOfAdalat = (String) ((ArrayAdapter) parent.getAdapter()).getItem(position);
                } else {
                    typeOfAdalat = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populateCustomerSpiner() {
        List<String> list_pay_mode = Arrays.asList(new String[]{"--Select Customer Type--", "Trader", "Manufacturer", "Dealer", "Packer", "Repairer"});
        sp_con_type.setAdapter(new ArrayAdapter<>(PaymentEntryActivity.this, android.R.layout.simple_list_item_1, list_pay_mode));
        sp_con_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    customerType = (String) ((ArrayAdapter) parent.getAdapter()).getItem(position);
                } else {
                    customerType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void ShowDialog() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        datedialog = new DatePickerDialog(PaymentEntryActivity.this,
                mDateSetListener, mYear, mMonth, mDay);
        datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            try {
                if (mDay < 10 && (mMonth + 1) > 9) {
                    mDay = Integer.parseInt("0" + mDay);
                    date_sel.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append("0" + mDay));
                } else if ((mMonth + 1) < 10 && mDay > 9) {
                    mMonth = Integer.parseInt("0" + mMonth);
                    date_sel.setText(new StringBuilder().append(mYear).append("-").append("0" + (mMonth + 1)).append("-").append(mDay));
                } else if ((mMonth + 1) < 10 && mDay < 10) {
                    mDay = Integer.parseInt("0" + mDay);
                    mMonth = Integer.parseInt("0" + mMonth);
                    date_sel.setText(new StringBuilder().append(mYear).append("-").append("0" + (mMonth + 1)).append("-").append("0" + mDay));
                } else {
                    date_sel.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void AddOgrassPay(View view) {
        if (payMode.equals("")){
            Toast.makeText(PaymentEntryActivity.this, "Please Select Mode", Toast.LENGTH_SHORT).show();
        }
        else if (date_sel.getText().toString().trim().equals("")) {
            Toast.makeText(PaymentEntryActivity.this, "Please Select Date !", Toast.LENGTH_SHORT).show();
        } else if (edit_court_name.getText().toString().trim().length() < 3) {
            text_input_court_name.setError("enter valid name");
        } else if (payMode.equals("")){
            Toast.makeText(PaymentEntryActivity.this, "Please Select Customer Type !", Toast.LENGTH_SHORT).show();
        }
        else if (edit_grn.getText().toString().trim().length()<5){
            text_input_grn.setError("enter valid grn");
        } else if (edit_pay_amt.getText().toString().trim().equals("")||edit_pay_amt.getText().toString().trim().equals("0")){
            edit_pay_amt.setError("valid amount");
        }
        else if (Double.parseDouble(edit_pay_amt.getText().toString().trim())<0){
            edit_pay_amt.setError("valid amount");
        }else {
            ll_pay.setVisibility(View.GONE);
            card_pay.setVisibility(View.VISIBLE);
            ll_com_consumer.setVisibility(View.VISIBLE);
            text_date.setText(date_sel.getText().toString().trim());
            text_court.setText(edit_court_name.getText().toString().trim());
            text_grn.setText(edit_grn.getText().toString().trim());
            text_amt.setText(edit_pay_amt.getText().toString().trim());
            text_mode.setText(payMode);
            text_adalat.setText(typeOfAdalat);
            text_total_amt.setText(edit_pay_amt.getText().toString().trim());
            date_sel.setText("");
            edit_court_name.setText("");
            edit_grn.setText("");
            edit_pay_amt.setText("");
            sp_paymode.setSelection(0);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(PaymentEntryActivity.this)
                .setTitle("Really Close ?")
                .setMessage("Are you sure want to close ? This will lost your currently filled data .")
                .setPositiveButton(android.R.string.no, null)
                .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        PaymentEntryActivity.super.onBackPressed();
                        //finish();
                    }
                }).create().show();
    }
}