package com.splashScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dataProvider.DataProvider;
import com.dataProvider.GetDataTask;
import com.roomfinder.MainActivity;
import com.roomfinder.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {

	private String TAG = SplashScreenActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		Log.i(TAG, "SplashScreenActivity - onCreate reached!!!");
		
		TextView textView = (TextView) findViewById(R.id.load);
		textView.setText("Loading...");
		
		new BackgroundSplashTask().execute(this);

	}


	private class BackgroundSplashTask extends AsyncTask {
		
		private String TAG = BackgroundSplashTask.class.getName();
		private String url = "https://raw.githubusercontent.com/CollabGoedel/roomFinder/master/nameRoom.json";

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(Void... arg0) {

			Log.i(TAG, "SplashScreenActivity - doInBackground without params reached!!!");
			return null;
		}

		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			
			Log.i(TAG, "SplashScreenActivity - start main activity");
			
			Intent i = new Intent(SplashScreenActivity.this,
					MainActivity.class);
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
					HttpResponse response = new DefaultHttpClient().execute(new HttpGet(url));
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
