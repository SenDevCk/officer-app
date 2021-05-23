package org.nic.lmd.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.json.JSONObject;
import org.nic.lmd.officerapp.MainActivity;
import org.nic.lmd.officerapp.OTPValidationActivity;
import org.nic.lmd.officerapp.R;
import org.nic.lmd.preferences.CommonPref;
import org.nic.lmd.preferences.GlobalVariable;
import org.nic.lmd.utilities.Urls_this_pro;
import org.nic.lmd.utilities.WebHandler;


public class LoginLoader extends AsyncTask<String, Void, String> {

    Activity activity;
    private ProgressDialog dialog1;
    //private AlertDialog alertDialog;
    String[] params;

    public interface LoginListener {
        public void success(String otp);
    }

    static LoginListener loginListener;

    public LoginLoader(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        //alertDialog = new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {
        this.dialog1.setCanceledOnTouchOutside(false);
        this.dialog1.setMessage("LOGIN LOADING...");
        this.dialog1.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String json_res = null;
        params = strings;
        try {
            JSONObject jsonObject = new JSONObject();
            if (params.length == 2) {
                jsonObject.accumulate("userId", strings[0]);
                jsonObject.accumulate("passWord", strings[1]);
                jsonObject.accumulate("otp", JSONObject.NULL);
                jsonObject.accumulate("userType", JSONObject.NULL);
                json_res = WebHandler.callByPost(jsonObject.toString(), Urls_this_pro.LOG_IN_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return json_res;
        }
        return json_res;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        if (dialog1.isShowing()) dialog1.dismiss();
        if (res != null) {
            if (WebHandler.hasError(res)) {
                Toast.makeText(activity, "" + WebHandler.getError(res), Toast.LENGTH_SHORT).show();
            } else {
                try {
                    System.out.println(res);
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.has("statusCode")) {
                        if (jsonObject.getInt("statusCode") == 200) {
                        /*
                            && jsonObject.getString("status").trim().equals("OTP Validation")
                            Intent intent=new Intent(activity, OTPValidationActivity.class);
                            intent.putExtra("userID",userid);
                            intent.putExtra("pass",password);
                            activity.startActivity(intent);
                            activity.finish();
                        */
                            if (params.length == 2) {
                                CommonPref.setUserDetails(activity, res);
                                activity.findViewById(R.id.ll_otp).setVisibility(View.VISIBLE);
                                activity.findViewById(R.id.ll_user).setVisibility(View.GONE);
                                Button button = activity.findViewById(R.id.button_login);
                                button.setText("Proceed");
                                loginListener.success(jsonObject.getJSONObject("data").getString("captcha"));
                            }
                        } else {
                            Toast.makeText(activity, "" + jsonObject.getString("status"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("error", "Status code not found in json");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(activity, "Server Problem Or Somthing went wrong !", Toast.LENGTH_SHORT).show();
            Log.e("log", "null on LoginLoader");
        }
    }

    public static void initiateLogin(LoginListener loginListener1) {
        loginListener = loginListener1;
    }
}

