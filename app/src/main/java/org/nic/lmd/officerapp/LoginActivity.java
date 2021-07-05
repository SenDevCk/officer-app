package org.nic.lmd.officerapp;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Telephony;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nic.lmd.entities.UserData;
import org.nic.lmd.interfaces.DataListener;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.services.LocationLoader;
import org.nic.lmd.services.LoginLoader;
import org.nic.lmd.smsRecever.SmsListener;
import org.nic.lmd.smsRecever.SmsReceiver;
import org.nic.lmd.utilities.Utiilties;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    Button register, button_login;
    TextView text_log,check_con_pay;
    EditText user_name, password, otp_reg;
    Spinner sp_loc_type, sp_area, sp_position;
    LinearLayout ll_otp, ll_area_role;
    String area = "", location = "", role = "";
    UserData userData;
    JSONArray jsonArray_loc, jsonArray_area;
    SmsReceiver smsReceiver;
    IntentFilter filter;
    String otp_by_service="";
    ImageView img_login;
    private boolean validated = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        img_login = findViewById(R.id.img_login);
        ll_otp = findViewById(R.id.ll_otp);
        ll_area_role = findViewById(R.id.ll_area_role);
        ll_otp.setVisibility(View.GONE);
        button_login = findViewById(R.id.button_login);
        button_login.setText("Login");
        register = findViewById(R.id.button_signup);
        check_con_pay = findViewById(R.id.check_con_pay);
        check_con_pay.setVisibility(View.GONE);
        text_log = findViewById(R.id.text_log);
        text_log.setText(getResources().getString(R.string.app_name));
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        otp_reg = findViewById(R.id.otp_reg);
        sp_loc_type = findViewById(R.id.sp_loc_type);
        sp_area = findViewById(R.id.sp_area);
        sp_position = findViewById(R.id.sp_position);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/header_font.ttf");
        text_log.setTypeface(face);
        userData = CommonPref.getUserDetails(LoginActivity.this);
    }


    public void Login(View view) {
        if (user_name.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter User Name !", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password !", Toast.LENGTH_SHORT).show();
        } else if (!Utiilties.isOnline(LoginActivity.this)) {
            Toast.makeText(this, "GO ONLINE !", Toast.LENGTH_SHORT).show();
        } else {
            UserData userData = CommonPref.getUserDetails(LoginActivity.this);
            if (button_login.getText().toString().trim().equals("Login")) {
                if (!CommonPref.getOTPValidated(LoginActivity.this)) {
                    filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
                    smsReceiver = new SmsReceiver();
                    registerReceiver(smsReceiver, filter);
                    SmsReceiver.bindListener(new SmsListener() {
                        @Override
                        public void messageReceived(String messageText) {
                            // text_resend.setVisibility(View.GONE);
                            Log.d("messageText", "" + messageText);
                            //String otp_by_sms = Utiilties.extractInt(messageText).trim();
                            String[] tokens=messageText.split(",");
                            otp_reg.setText(tokens[1].trim().substring(0,4));
                        }
                    });
                    LoginLoader.initiateLogin(new LoginLoader.LoginListener() {
                        @Override
                        public void success(String otp) {
                            otp_by_service = otp;
                            countDownTimer = new CountDownTimer(300000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    check_con_pay.setVisibility(View.VISIBLE);
                                    check_con_pay.setText(Html.fromHtml("OTP Valid for <b style=\"color:Tomato;\">:" + (millisUntilFinished / 1000) + "</b> intervals."));
                                    check_con_pay.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                                    //here you can have your logic to set text to edittext
                                }
                                public void onFinish() {

                                    check_con_pay.setVisibility(View.GONE);
                                    otp_by_service="";
                                    ll_otp.setVisibility(View.GONE);
                                    button_login.setText("Login");
                                    findViewById(R.id.ll_user).setVisibility(View.VISIBLE);
                                    register.setVisibility(View.VISIBLE);
                                    setHeader(80,35);
                                }
                            }.start();
                            loadData(1, "", "");
                        }
                    });
                    new LoginLoader(LoginActivity.this).execute(user_name.getText().toString().trim(), password.getText().toString().trim());
                } else {
                    if (userData.getUserid().equals(user_name.getText().toString().trim()) && userData.getPassword().equals(password.getText().toString().trim()))
                    {
                        ll_otp.setVisibility(View.VISIBLE);
                        findViewById(R.id.ll_user).setVisibility(View.GONE);
                        button_login.setText("Proceed");
                        otp_reg.setVisibility(View.GONE);
                        register.setVisibility(View.GONE);
                        validated = true;
                        loadData(1, "", "");
                    }else{
                        Toast.makeText(LoginActivity.this, "UserId or Password Error !", Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                if (location.equals("") || area.equals("") || role.equals("")) {
                    Toast.makeText(this, "Select area ,location ,role", Toast.LENGTH_SHORT).show();

                }
                else if (!validated && otp_reg.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "enter OTP !", Toast.LENGTH_SHORT).show();
                }
                else if (!validated && !otp_by_service.equals(otp_reg.getText().toString().trim())) {
                    Toast.makeText(this, "OTP is not matching !", Toast.LENGTH_SHORT).show();
                 }else {
                    //new LoginLoader(LoginActivity.this).execute(location, role, area);
                    startMainActivity();

                }
            }

        }
    }

    private void startMainActivity() {
        CommonPref.setOTPValidated(LoginActivity.this, true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("locationType", location);
        intent.putExtra("loginRole", role);
        intent.putExtra("loginLocation", area);
        startActivity(intent);
        finish();
    }


    public void Register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void populateAllSpinner(final JSONArray jsonArray, int flag_sp) {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<String> arrayList3 = new ArrayList<>();
        try {
            if (flag_sp == 1) {
                jsonArray_loc = jsonArray;
                arrayList.add("--Select Location--");
            } else if (flag_sp == 2) {
                jsonArray_area = jsonArray;
                arrayList2.add("--Select Area--");
                arrayList3.add("--Select Role--");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                switch (flag_sp) {
                    case 1:
                        arrayList.add(jsonObject.getString("name"));
                        break;
                    case 2:
                        arrayList2.add(jsonObject.getString("locationName"));
                        arrayList3.add(jsonObject.getString("roleName"));
                        break;
                }
            }
            if (flag_sp == 1) {
                sp_loc_type.setAdapter(new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_list_item_1, arrayList));
                sp_loc_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            try {
                                UserData userData = CommonPref.getUserDetails(LoginActivity.this);
                                JSONObject jsonObject = jsonArray_loc.getJSONObject(position - 1);
                                location = jsonObject.getString("value");
                                loadData(2, location, userData.getStaffId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            location = "";
                            loadData(2, location, userData.getStaffId());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } else if (flag_sp == 2) {
                ll_area_role.setVisibility(View.VISIBLE);
                setHeader(40,20);
                sp_area.setAdapter(new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_list_item_1, arrayList2));
                sp_position.setAdapter(new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_list_item_1, arrayList3));
                sp_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position > 0) {
                            try {
                                JSONObject jsonObject = jsonArray_area.getJSONObject(position - 1);
                                area = jsonObject.getString("officeCode");
                                role = jsonObject.getString("roleName");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            area = "";
                            role = "";
                        }
                        sp_position.setSelection(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            sp_position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        try {
                            JSONObject jsonObject = jsonArray_area.getJSONObject(position - 1);
                            area = jsonObject.getString("officeCode");
                            location = jsonObject.getString("roleName");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        area = "";
                        role = "";
                    }
                    sp_area.setSelection(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeader(int img_size, int text_size) {
        img_login.getLayoutParams().height = img_size;
        img_login.getLayoutParams().width = img_size;
        img_login.requestLayout();
        text_log.setTextSize(text_size);
        text_log.requestLayout();
    }

    public void loadData(int f, String data1, String data2) {
        LocationLoader.listenForData(new DataListener() {
            @Override
            public void dataFound(JSONArray data, int flag_data_type) {
                populateAllSpinner(data, flag_data_type);
            }

            @Override
            public void dataNotFound(String msg) {
                Toast.makeText(LoginActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                ll_area_role.setVisibility(View.GONE);
            }
        });
        LocationLoader loader = new LocationLoader(LoginActivity.this, f);
        if (f == 1) loader.execute();
        else loader.execute(data1, data2);
    }
}