package com.splashScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dataProvider.DataProvider;
import com.dataProvider.Login;
import com.roomfinder.MainActivity;
import com.roomfinder.R;
import com.roomfinder.RoomActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import utils.Constants;

public class SplashScreenActivity extends Activity implements View.OnClickListener{

	private String TAG = SplashScreenActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		Log.i(TAG, "SplashScreenActivity - onCreate reached!!!");
		
		TextView textView = (TextView) findViewById(R.id.load);
		Login login = new Login();
		
		if (login.isLogin(this)){
			textView.setText("Loading...");
			new BackgroundSplashTask().execute(this);
		} else {
			
			TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
			TextView buttonPassword = (TextView) findViewById(R.id.loginButton);
			TextView enterTextViewPassword = (TextView) findViewById(R.id.loginEditText);
			headlinePassword.setVisibility(View.VISIBLE);
			buttonPassword.setVisibility(View.VISIBLE);
			enterTextViewPassword.setVisibility(View.VISIBLE);
			textView.setText("Waiting for Password ...");
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(enterTextViewPassword.getWindowToken(), 0);
			imm.hideSoftInputFromWindow(buttonPassword.getWindowToken(), 0);
			buttonPassword.setOnClickListener(this);
		}

	}
	
	
	@Override
	public void onClick(View v) {
		
		Login login = new Login();
		
	      switch (v.getId()) {
          case R.id.loginButton:
        	  Log.i(TAG, "SplashScreenActivity - Button login pressed");
        	  TextView passwordEditText = (TextView) findViewById(R.id.loginEditText);
        	  String password = passwordEditText.getText().toString();
        	  
        	  if (login.checkPassword(password, this)) {
        		  TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        		  TextView textView = (TextView) findViewById(R.id.load);
        		  textView.setText("Loading...");
        		  headlinePassword.setText("Password accept - loading ...");
        		  new BackgroundSplashTask().execute(this);
        	  } else {
        		  TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        		  headlinePassword.setText("Password not accept !!!");
        	  }

        	  
              break;
          default:
              
              break;
      }
		
	}


	private class BackgroundSplashTask extends AsyncTask {
		
		private String TAG = BackgroundSplashTask.class.getName();
		private String url = "https://raw.githubusercontent.com/CollabGoedel/roomFinder/master/nameRoom.json";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}



		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			
			Log.i(TAG, "SplashScreenActivity - start main activity");
			
			Intent i = new Intent(SplashScreenActivity.this,
					RoomActivity.class);
			startActivity(i);
			finish();
		}

		protected Object doInBackground(Object... params) {
			Log.i(TAG, "SplashScreenActivity - doInBackground with params reached!!!");
			//Log.i(TAG, "SplashScreenActivity - url: " + url);
			int SPLASH_SHOW_TIME = 5000;

			try {
				
				DataProvider dataProvider = new DataProvider();
				boolean isAlreadyStored = false;
				isAlreadyStored = dataProvider.isDataStored((Activity)params[0]);
				
				if (!isAlreadyStored) {
					HttpResponse response = new DefaultHttpClient().execute(new HttpGet(Constants.NAME_ROOM_URL));
					BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
					String line = "";

					while ((line = rd.readLine()) != null) {
						
						//Log.i(TAG, "SplashScreenActivity - current line: " + line);
						dataProvider.addNewEntry(line, (Activity)params[0]);

					}
					rd.close();
				}
				

				Thread.sleep(SPLASH_SHOW_TIME);

			} catch (Exception e) {
				Log.e(TAG, "SplashScreenActivity with Exception: " + e.getMessage());
			}
			return null;
		}

	}
}
