package com.dataProvider;

import com.splashScreen.SplashScreenActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import utils.Constants;

public class Login {
	
	private String TAG = Login.class.getName();
	
	public boolean isLogin (Activity activity) {
		
		SharedPreferences sp_login = activity.getApplicationContext().getSharedPreferences("LOGIN", 0);
		boolean bool = sp_login.getBoolean("login", false);;	
		Log.i(TAG, "Login - bool: " + bool);
		
		return bool;
	}
	
	
	public boolean checkPassword (String password, Activity activity) {
		
		if (password.equals(Constants.LOGIN)) {
			setLogin(activity);
			return true;
		}
		return false;
	}
	
	private void setLogin(Activity activity) {
		
		SharedPreferences sp_login = activity.getApplicationContext().getSharedPreferences("LOGIN", 0);
		SharedPreferences.Editor editor = sp_login.edit();		
		editor.putBoolean("login", true);
		editor.apply(); 
	}

}
