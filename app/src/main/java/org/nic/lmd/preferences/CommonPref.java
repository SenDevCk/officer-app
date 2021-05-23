package org.nic.lmd.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import org.json.JSONObject;
import org.nic.lmd.entities.UserData;


/**
 * Created by CKS on 15/06/2018.
 */
public class CommonPref {


	CommonPref() {

	}



	public static void setCheckUpdate(Activity context, boolean first) {
		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putBoolean("first", first);

		editor.commit();

	}

	public static boolean getCheckUpdate(Context context) {
		String key = "_USER_DETAILS";
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		return prefs.getBoolean("first", false);
	}

    public static void setUserDetails(Activity activity,String res) {
		/*{"statusCode": 1,"status": "success","remarks": null,"list": null,"data": {"userId": "adm_usr","password": "admin","role": "CTRL","designation": "Joint Director Agriculture-cum-Controller","name": "Nitish Singh","location": "Patna","address": "Patil Nagar","contact": "9876543210","email": "abc@gmail.com","estbSubdivId": 106,"captcha": null}}*/
		String key = "_USER_DETAILS";

		SharedPreferences prefs = activity.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		try {
			Log.d("log",res);
			JSONObject jsonObject1=new JSONObject(res);
			JSONObject jsonObject=jsonObject1.getJSONObject("data");
			if (jsonObject.has("userId"))editor.putString("userId", jsonObject.getString("userId"));
			if (jsonObject.has("staffId"))editor.putString("staffId", jsonObject.getString("staffId"));
			if (jsonObject.has("password"))editor.putString("password", jsonObject.getString("password"));
			if (jsonObject.has("role"))editor.putString("role", jsonObject.getString("role"));
			if (jsonObject.has("designation"))editor.putString("designation", jsonObject.getString("designation"));
			if (jsonObject.has("name"))editor.putString("name", jsonObject.getString("name"));
			if (jsonObject.has("location"))editor.putString("location", jsonObject.getString("location"));
			if (jsonObject.has("address"))editor.putString("address", jsonObject.getString("address"));
			if (jsonObject.has("contact"))editor.putString("contact", jsonObject.getString("contact"));
			if (jsonObject.has("email"))editor.putString("email", jsonObject.getString("email"));
			if (jsonObject.has("estbSubdivId"))editor.putString("estbSubdivId", jsonObject.getString("estbSubdivId"));
			if (jsonObject.has("captcha"))editor.putString("captcha", jsonObject.getString("captcha"));
		}catch (Exception e){
			e.printStackTrace();
		}
		editor.commit();
    }

    public static UserData getUserDetails(Activity activity){
		String key = "_USER_DETAILS";
		UserData userData=new UserData();
		SharedPreferences prefs = activity.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		userData.setUserid(prefs.getString("userId", ""));
		userData.setStaffId(prefs.getString("staffId", ""));
		userData.setPassword(prefs.getString("password", ""));
		userData.setContact(prefs.getString("role", ""));
		userData.setDesignation(prefs.getString("designation", ""));
		userData.setName(prefs.getString("name", ""));
		userData.setLocation(prefs.getString("location", ""));
		userData.setAddress(prefs.getString("address", ""));
		userData.setContact(prefs.getString("contact", ""));
		userData.setEmail(prefs.getString("email", ""));
		userData.setEstbSubdivId(prefs.getString("estbSubdivId", ""));
		return userData;
	}

	public static void setOTPValidated(Activity context, boolean first) {
		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putBoolean("isValidatedOtp", first);

		editor.commit();

	}

	public static boolean getOTPValidated(Context context) {
		String key = "_USER_DETAILS";
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		return prefs.getBoolean("isValidatedOtp", false);
	}

	public static boolean clearPreferenceLogout(Activity context){
		String key = "_USER_DETAILS";
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		return true;
	}
}
