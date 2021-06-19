package org.nic.lmd.officerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    String subDiv = "";
    public static int yearSelected, monthSelected;
    EditText edit_m_current, edit_d_current, edit_r_current, edit_p_current;
    TextView text_m_previous, text_d_previous, text_r_previous, text_p_previous;
    TextView text_m_tot_sum, text_d_tot_sum, text_r_tot_sum, text_p_tot_sum;
    TextView text_row_tot1, text_row_tot2, text_row_tot3;
    RenevalAndRegistrationFee renevalAndRegistrationFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_reg_fee_entry);
        subDiv = getIntent().getStringExtra("subDiv");
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
        edit_m_current = findViewById(R.id.edit_m_current);
        edit_d_current = findViewById(R.id.edit_d_current);
        edit_r_current = findViewById(R.id.edit_r_current);
        edit_p_current = findViewById(R.id.edit_p_current);
        addTextChange(edit_m_current, 'M');
        addTextChange(edit_d_current, 'D');
        addTextChange(edit_r_current, 'R');
        addTextChange(edit_p_current, 'P');
        text_m_previous = findViewById(R.id.text_m_previous);
        text_d_previous = findViewById(R.id.text_d_previous);
        text_r_previous = findViewById(R.id.text_r_previous);
        text_p_previous = findViewById(R.id.text_p_previous);

        text_m_tot_sum = findViewById(R.id.text_m_tot_sum);
        text_d_tot_sum = findViewById(R.id.text_d_tot_sum);
        text_r_tot_sum = findViewById(R.id.text_r_tot_sum);
        text_p_tot_sum = findViewById(R.id.text_p_tot_sum);

        text_row_tot1 = findViewById(R.id.text_row_tot1);
        text_row_tot2 = findViewById(R.id.text_row_tot2);
        text_row_tot3 = findViewById(R.id.text_row_tot3);
        //text_row_tot4=findViewById(R.id.text_row_tot4);

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
                //text_year_month.setClickable(false);
                GlobalVariable.m_id = 0;
                //GlobalVariable.m_id = Long.parseLong((String.valueOf(monthSelected).length() == 1) ? "" + String.valueOf(yearSelected).substring(1, 3) + "0" + monthSelected + (userData.getEstbSubdivId().equals("" ) ? 187 : userData.getEstbSubdivId()) + "000" : "" + String.valueOf(yearSelected).substring(1, 3) + "" + (userData.getEstbSubdivId().equals("" ) ? 187 : userData.getEstbSubdivId()) + "000" );
                GlobalVariable.m_id = Long.parseLong("" + String.valueOf(yearSelected).substring(2, 4) + ((String.valueOf(monthSelected).length() == 1) ? "0" + monthSelected : "" + monthSelected) +  Integer.parseInt(subDiv));
                upload_data.setVisibility(View.GONE);
                callServiceForData(monthSelected, yearSelected, false);
            }
        });
    }

    private void addTextChange(EditText editText, char type) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edit_m_current.getText().toString().trim().equals("") && !edit_d_current.getText().toString().trim().equals("") && !edit_r_current.getText().toString().trim().equals("") && !edit_p_current.getText().toString().trim().equals("")) {
                    if (Double.parseDouble(edit_m_current.getText().toString().trim()) >= 0 && Double.parseDouble(edit_d_current.getText().toString().trim()) >= 0 && Double.parseDouble(edit_r_current.getText().toString().trim()) >= 0 && Double.parseDouble(edit_r_current.getText().toString().trim()) >= 0) {
                        switch (type) {
                            case 'M':
                                text_m_tot_sum.setText("" + (Double.parseDouble(text_m_previous.getText().toString().trim()) + Double.parseDouble(edit_m_current.getText().toString().trim())));
                                break;
                            case 'D':
                                text_d_tot_sum.setText("" + (Double.parseDouble(text_d_previous.getText().toString().trim()) + Double.parseDouble(edit_d_current.getText().toString().trim())));
                                break;
                            case 'R':
                                text_r_tot_sum.setText("" + (Double.parseDouble(text_r_previous.getText().toString().trim()) + Double.parseDouble(edit_r_current.getText().toString().trim())));
                                break;
                            case 'P':
                                text_p_tot_sum.setText("" + (Double.parseDouble(text_p_previous.getText().toString().trim()) + Double.parseDouble(edit_p_current.getText().toString().trim())));
                                break;
                            default:
                                Toast.makeText(Ren_RegFeeEntryActivity.this, "Default show", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        showGrandTotal();
                    }
                } else {
                    Log.e("e-log", "blank data");
                }
            }
        });
        //editText.setText("0" );
        addFocusChangeListner(editText);
    }

    private void addFocusChangeListner(EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editText.getText().toString().trim().equals("0") || editText.getText().toString().trim().equals("0.0")) {
                        editText.setText("");
                    }
                } else {
                    if (editText.getText().toString().trim().equals("")) {
                        editText.setText("0");
                    }
                }
            }
        });
    }

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    private void callServiceForData(int month_g, int year_g, boolean isPre) {
        Call<MyResponse<RenevalAndRegistrationFee>> call1 = null;
        progressDialog = new ProgressDialog(Ren_RegFeeEntryActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
      /*  if (monthSelected == 1)
            call1 = apiInterface.doGetRenRegData(12, yearSelected - 1, (subDiv.equals("")) ? "187" : subDiv);
        else*/
            call1 = apiInterface.doGetRenRegData(month_g, year_g, subDiv);
        call1.enqueue(new Callback<MyResponse<RenevalAndRegistrationFee>>() {
            @Override
            public void onResponse(Call<MyResponse<RenevalAndRegistrationFee>> call, Response<MyResponse<RenevalAndRegistrationFee>> response) {
                if (progressDialog.isShowing()) progressDialog.dismiss();
                renevalAndRegistrationFee=null;
                if (response.body() != null) {
                    if (response.body().getStatusCode() == 200) {
                        renevalAndRegistrationFee = response.body().getData();
                        upload_data.setVisibility(View.VISIBLE);
                        populateData(isPre);
                    } else {
                        if (!isPre) {
                            if (monthSelected != 4) {
                                if (monthSelected == 1)
                                    callServiceForData(12, year_g - 1, true);
                                else callServiceForData(month_g - 1, year_g, true);
                            } else {
                                upload_data.setVisibility(View.VISIBLE);
                                populateData(false);
                            }
                        } else {
                            upload_data.setVisibility(View.GONE);
                            populateData(false);
                            Toast.makeText(Ren_RegFeeEntryActivity.this, "" + response.body().getRemarks(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<RenevalAndRegistrationFee>> call, Throwable t) {
                Log.e("error", t.getMessage());
                if (progressDialog.isShowing()) progressDialog.dismiss();
                Toast.makeText(Ren_RegFeeEntryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void populateData(boolean isPre) {
        if (isPre) {
            text_m_previous.setText("" + ((renevalAndRegistrationFee != null) ? renevalAndRegistrationFee.getMTotalCurrent() : 0));
            text_d_previous.setText("" + ((renevalAndRegistrationFee != null) ? renevalAndRegistrationFee.getDTotalCurrent() : 0));
            text_r_previous.setText("" + ((renevalAndRegistrationFee != null) ? renevalAndRegistrationFee.getRTotalCurrent() : 0));
            text_p_previous.setText("" + ((renevalAndRegistrationFee != null) ? renevalAndRegistrationFee.getPTotalCurrent() : 0));
            edit_m_current.setText("0");
            edit_d_current.setText("0");
            edit_r_current.setText("0");
            edit_p_current.setText("0");
        } else {
            text_m_previous.setText("" + ((renevalAndRegistrationFee != null) ? (renevalAndRegistrationFee.getMTotalCurrent() - renevalAndRegistrationFee.getMCurrent()) : 0));
            text_d_previous.setText("" + ((renevalAndRegistrationFee != null) ? (renevalAndRegistrationFee.getDTotalCurrent() - renevalAndRegistrationFee.getDCurrent()) : 0));
            text_r_previous.setText("" + ((renevalAndRegistrationFee != null) ? (renevalAndRegistrationFee.getRTotalCurrent() - renevalAndRegistrationFee.getRCurrent()) : 0));
            text_p_previous.setText("" + ((renevalAndRegistrationFee != null) ? (renevalAndRegistrationFee.getPTotalCurrent() - renevalAndRegistrationFee.getPCurrent()) : 0));
            edit_m_current.setText(""+((renevalAndRegistrationFee != null)?renevalAndRegistrationFee.getMCurrent():0));
            edit_d_current.setText(""+((renevalAndRegistrationFee != null)?renevalAndRegistrationFee.getDCurrent():0));
            edit_r_current.setText(""+((renevalAndRegistrationFee != null)?renevalAndRegistrationFee.getRCurrent():0));
            edit_p_current.setText(""+((renevalAndRegistrationFee != null)?renevalAndRegistrationFee.getPCurrent():0));
        }

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void uploadData() {
        if (isEmpty(edit_m_current)) {
            Toast.makeText(this, "Enter manufacture Amount !", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(edit_d_current)) {
            Toast.makeText(this, "Enter deler Amount !", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(edit_r_current)) {
            Toast.makeText(this, "Enter repairer Amount !", Toast.LENGTH_SHORT).show();
        }
        if (isEmpty(edit_p_current)) {
            Toast.makeText(this, "Enter packer Amount !", Toast.LENGTH_SHORT).show();
        } else {
            RenevalAndRegistrationFee revenueMonthlyTarget = new RenevalAndRegistrationFee();
            revenueMonthlyTarget.setRenRegId(Long.parseLong("" + String.valueOf(yearSelected).substring(2, 4) + ((String.valueOf(monthSelected).length() == 1) ? "0" + monthSelected : "" + monthSelected) + ((subDiv.equals("")) ? 187 : Integer.parseInt(subDiv))));
            revenueMonthlyTarget.setMCurrent(Double.parseDouble(edit_m_current.getText().toString().trim()));
            revenueMonthlyTarget.setMTotalCurrent(Double.parseDouble(text_m_tot_sum.getText().toString().trim()));
            revenueMonthlyTarget.setDCurrent(Double.parseDouble(edit_d_current.getText().toString().trim()));
            revenueMonthlyTarget.setDTotalCurrent(Double.parseDouble(text_d_tot_sum.getText().toString().trim()));
            revenueMonthlyTarget.setRCurrent(Double.parseDouble(edit_r_current.getText().toString().trim()));
            revenueMonthlyTarget.setRTotalCurrent(Double.parseDouble(text_r_tot_sum.getText().toString().trim()));
            revenueMonthlyTarget.setPCurrent(Double.parseDouble(edit_p_current.getText().toString().trim()));
            revenueMonthlyTarget.setPTotalCurrent(Double.parseDouble(text_p_tot_sum.getText().toString().trim()));
            SubDivision subDivision = new SubDivision();
            subDivision.setId(Integer.parseInt(subDiv));
            revenueMonthlyTarget.setSub_div(subDivision);
            revenueMonthlyTarget.setMonth(monthSelected);
            revenueMonthlyTarget.setYear(yearSelected);
            revenueMonthlyTarget.setUser_id(CommonPref.getUserDetails(Ren_RegFeeEntryActivity.this).getUserid());
            progressDialog = new ProgressDialog(Ren_RegFeeEntryActivity.this);
            progressDialog.setTitle("Upload...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            apiInterface = APIClient.getClient(Urls_this_pro.RETROFIT_BASE_URL2).create(APIInterface.class);
            Call<MyResponse<String>> call1 = apiInterface.saveRenRegFee(revenueMonthlyTarget);
            call1.enqueue(new Callback<MyResponse<String>>() {
                @Override
                public void onResponse(Call<MyResponse<String>> call, Response<MyResponse<String>> response) {
                    Log.e("elog", "" + response.body());
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    MyResponse<String> myResponse = null;
                    if (response.body() != null) myResponse = response.body();
                    if (myResponse == null) {
                        Toast.makeText(Ren_RegFeeEntryActivity.this, "Null Response found !", Toast.LENGTH_SHORT).show();
                    } else if (myResponse.getStatusCode() == 200) {
                        Toast.makeText(Ren_RegFeeEntryActivity.this, "Data Successfully Sent", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Ren_RegFeeEntryActivity.this, "" + myResponse.getRemarks(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MyResponse<String>> call, Throwable t) {
                    Log.e("error", t.getMessage());
                    Toast.makeText(Ren_RegFeeEntryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing()) progressDialog.dismiss();
                    call.cancel();
                }
            });
        }
    }

    private void showGrandTotal() {
        text_row_tot1.setText("" + (Double.parseDouble(text_m_previous.getText().toString().trim()) + Double.parseDouble(text_d_previous.getText().toString().trim()) + Double.parseDouble(text_r_previous.getText().toString().trim())));
        text_row_tot2.setText("" + (Double.parseDouble(edit_m_current.getText().toString().trim()) + Double.parseDouble(edit_d_current.getText().toString().trim()) + Double.parseDouble(edit_r_current.getText().toString().trim())));
        text_row_tot3.setText("" + (Double.parseDouble(text_m_tot_sum.getText().toString().trim()) + Double.parseDouble(text_d_tot_sum.getText().toString().trim()) + Double.parseDouble(text_r_tot_sum.getText().toString().trim())));
    }

    @Override
    public void onClick(View v) {
        uploadData();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(Ren_RegFeeEntryActivity.this)
                .setTitle("Really Exit ?")
                .setMessage("Are you sure want to close ?")
                .setPositiveButton(android.R.string.no, null)
                .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Ren_RegFeeEntryActivity.super.onBackPressed();
                        //finish();
                    }
                }).create().show();
    }
}