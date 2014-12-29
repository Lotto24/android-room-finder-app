package com.data;

import android.app.Activity;
import android.content.SharedPreferences;

import com.utils.Constants;

import timber.log.Timber;

public class Login {

	public boolean isLogin (Activity activity) {
		
		SharedPreferences sp_login = activity.getApplicationContext().getSharedPreferences("LOGIN", 0);
		boolean bool = sp_login.getBoolean("login", false);
        Timber.d("Login " + bool);
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
