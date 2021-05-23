package org.nic.lmd.officerapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;

import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {

    EditText mobile, user_name, password, con_password, otp_reg;
    Button button_submit;
    int flag = 0;
    LinearLayout ll_reg, ll_reg1;
    ProgressDialog progressDialog;
    JSONObject userJsonObject;
    char[] generetedOtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mobile = findViewById(R.id.mobile);
        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        con_password = findViewById(R.id.con_password);
        otp_reg = findViewById(R.id.otp_reg);
        otp_reg.setVisibility(View.GONE);
        ll_reg = findViewById(R.id.ll_reg);
        ll_reg1 = findViewById(R.id.ll_reg1);
        ll_reg1.setVisibility(View.GONE);
        button_submit = findViewById(R.id.button_submit);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    if (mobile.getText().toString().trim().length() < 10) {
                        Toast.makeText(RegistrationActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                    } else {
                        new IsMobileExit().execute(mobile.getText().toString().trim());
                    }
                } else if (flag == 1) {
                    if (otp_reg.getText().toString().trim().length() < 6) {
                        Toast.makeText(RegistrationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                    } else if (!otp_reg.getText().toString().trim().equals(String.valueOf(generetedOtp))) {
                        Toast.makeText(RegistrationActivity.this, "OTP Mismatch", Toast.LENGTH_SHORT).show();
                    } else {
                        ll_reg1.setVisibility(View.VISIBLE);
                        flag = 2;
                        button_submit.setText("Register");
                        otp_reg.setVisibility(View.GONE);
                    }
                } else if (flag == 2) {
                    if (user_name.getText().toString().trim().length() < 3) {
                        Toast.makeText(RegistrationActivity.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().trim().equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                        password.setError("blank");
                    } else if (con_password.getText().toString().trim().equals("")) {
                        Toast.makeText(RegistrationActivity.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                        con_password.setError("blank");
                    } else if (!password.getText().toString().trim().equals(con_password.getText().toString().trim())) {
                        Toast.makeText(RegistrationActivity.this, "Password Not matching", Toast.LENGTH_SHORT).show();
                        con_password.setError("Mismatch");
                    } else {
                        new Register().execute(user_name.getText().toString().trim(), mobile.getText().toString().trim(), password.getText().toString().trim());
                    }
                }
            }
        });
    }


    private class IsMobileExit extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Verifying Mobile Number....");
        }

        @Override
        protected String doInBackground(String... strings) {
            return WebHandler.callByGet(Urls_this_pro.LOAD_MOBILE_EXIST + strings[0]);
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            hideProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("statusCode") == 200) {
                    otp_reg.setVisibility(View.VISIBLE);
                    flag = 1;
                    button_submit.setText("Verify");
                    userJsonObject = jsonObject.getJSONObject("data");
                    generetedOtp = generateOTP(6);
                    System.out.println(generetedOtp);
                    new SendOTP().execute(mobile.getText().toString().trim());
                } else {
                    flag = 0;
                    Toast.makeText(RegistrationActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SendOTP extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgressDialog("Getting OTP....");
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = null;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("arg1", strings[0]);
                jsonObject.accumulate("arg2", "Welcome to eBLMO App, Your OTP for registration is " + String.valueOf(generetedOtp) + " .Don't share this OTP with any .");
                res = WebHandler.callByPost(jsonObject.toString(), Urls_this_pro.LOAD_OTP_FOR_REGISTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            //hideProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(res);
               /* if (jsonObject.getInt("statusCode")==200){
                    ll_reg1.setVisibility(View.VISIBLE);
                    flag=2;
                    button_submit.setText("Register");
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static char[] generateOTP(int length) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

    private class Register extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Wait for Register....");
        }

        @Override
        protected String doInBackground(String... strings) {
            String res = null;
            try {
                userJsonObject.put("userId", strings[0].trim());
                userJsonObject.put("active", "A");
                userJsonObject.put("password", strings[2].trim());
                res = WebHandler.callByPost(userJsonObject.toString(), Urls_this_pro.LOAD_FINAL_REGISTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            hideProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.getInt("statusCode") == 200) {
                    Toast.makeText(RegistrationActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Registration not Successful", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) progressDialog.cancel();
        }
    }
}
