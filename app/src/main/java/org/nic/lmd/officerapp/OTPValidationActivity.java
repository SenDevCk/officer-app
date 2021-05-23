package org.nic.lmd.officerapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import org.json.JSONObject;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.Utiilties;
import org.nic.lmd.utilities.WebHandler;



public class OTPValidationActivity extends AppCompatActivity implements View.OnClickListener {

    OtpView otp_view;
    Button verify, go_to_home, but_sync_again;
    String version;
    TextView check_con_pay;
    Animation fade_in;
    String uid = "", pass = "";

    @Override
    protected void onStart() {
        GlobalVariable.active = true;
        super.onStart();
    }

    @Override
    protected void onStop() {
        GlobalVariable.active = false;
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_validation);
        uid = getIntent().getStringExtra("userID");
        pass = getIntent().getStringExtra("pass");
        init();
        //new GetOTPAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        otp_view = (OtpView) findViewById(R.id.otp_view);
        otp_view.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                verify.setClickable(false);
                new OTPValidationLoader().execute();
            }
        });
        verify =  findViewById(R.id.verify);
        verify.setVisibility(View.GONE);
        go_to_home =  findViewById(R.id.go_to_home);
        but_sync_again =  findViewById(R.id.but_sync_again);
        check_con_pay =  findViewById(R.id.check_con_pay);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        check_con_pay.setVisibility(View.GONE);
        but_sync_again.setVisibility(View.GONE);
        verify.setOnClickListener(this);
        go_to_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.go_to_home) {
            finish();
        } else {
            if (otp_view.getText().toString().length() < 6) {
                Toast.makeText(getApplicationContext(), "Enter Valid OTP", Toast.LENGTH_LONG).show();
            }else if (!Utiilties.isOnline(OTPValidationActivity.this)){
                Toast.makeText(this, "Internet Not Avalable !", Toast.LENGTH_SHORT).show();
            }else {
                verify.setClickable(false);
                new OTPValidationLoader().execute();
            }
        }
    }


    private class OTPValidationLoader extends AsyncTask<String, Void, String> {
        private final ProgressDialog dialog1 = new ProgressDialog(OTPValidationActivity.this);
        private final AlertDialog alertDialog = new AlertDialog.Builder(OTPValidationActivity.this).create();

        @Override
        protected void onPreExecute() {
            check_con_pay.setVisibility(View.GONE);
            this.dialog1.setCanceledOnTouchOutside(false);
            this.dialog1.setMessage("Processing...");
            this.dialog1.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("userId", uid);
                    jsonObject.accumulate("passWord", pass);
                    jsonObject.accumulate("otp", Integer.parseInt(otp_view.getText().toString().trim()));
                    jsonObject.accumulate("userType", JSONObject.NULL);
                    result = WebHandler.callByPost(jsonObject.toString(), Urls_this_pro.LOG_IN_URL);
                } else {
                    Log.e("log", "Your device must have atleast Kitkat or Above Version");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (this.dialog1.isShowing()) this.dialog1.cancel();
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("statusCode")) {
                        if (jsonObject.getInt("statusCode") == 1) {
                            CommonPref.setUserDetails(OTPValidationActivity.this,result);
                            Intent intent = new Intent(OTPValidationActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            verify.setClickable(true);
                            Toast.makeText(OTPValidationActivity.this, "" + jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                            setError(jsonObject.getString("status"));
                        }
                    } else {
                        Log.e("error", "Status code not found in json");
                        Toast.makeText(OTPValidationActivity.this, "JSON not found", Toast.LENGTH_SHORT).show();
                       setError("JSON not found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                verify.setClickable(true);
                setError("May be server problem");
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            alertDialog.setMessage("Transaction Canceled !");
            alertDialog.setCancelable(false);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
            //new DataBaseHelper(PinCodeActivity.this).deleteRequestedPayment(mruEntity.getCON_ID(), amount);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            alertDialog.setMessage(s);
            alertDialog.setCancelable(false);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.show();
            //new DataBaseHelper(PinCodeActivity.this).deleteRequestedPayment(mruEntity.getCON_ID(), amount);
        }

        private void setError(String err_string){
            check_con_pay.setVisibility(View.VISIBLE);
            check_con_pay.setText(err_string);
            check_con_pay.startAnimation(fade_in);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String reqString(String req_string) {
        byte[] chipperdata = Utiilties.rsaEncrypt(req_string.getBytes(), OTPValidationActivity.this);
        Log.e("chiperdata", new String(chipperdata));
        String encString = android.util.Base64.encodeToString(chipperdata, android.util.Base64.NO_WRAP);//.getEncoder().encodeToString(chipperdata);
        encString = encString.replaceAll("\\/", "SSLASH").replaceAll("\\=", "EEQUAL").replaceAll("\\+", "PPLUS");
        return encString;
    }


    @Override
    protected void onDestroy() {
        //countDownTimer.cancel();
        super.onDestroy();
    }
}
