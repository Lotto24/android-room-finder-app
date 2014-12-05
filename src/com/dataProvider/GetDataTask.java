package com.dataProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.dataProvider.*;
import com.splashScreen.SplashScreenActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class GetDataTask extends AsyncTask<String, Integer, Void> {

	private static final String TAG = GetDataTask.class.getName();
	private String url = "https://raw.githubusercontent.com/CollabGoedel/roomFinder/master/nameRoom.json";
//	private Activity activity;
//
//
//	public GetDataTask(Activity activity) {
//		this.activity = activity;
//	}

	protected Void doInBackground(String... params) {

		Log.i(TAG, "GetDataTask - doInBackground reached!!!");
		Log.i(TAG, "GetDataTask - url: " + url);

		try {
			DataProvider dataProvider = new DataProvider();
			HttpResponse response = new DefaultHttpClient().execute(new HttpGet(url));
			BufferedReader rd = new BufferedReader(new InputStreamReader(
			response.getEntity().getContent()));
			String line = "";

			while ((line = rd.readLine()) != null) {
				
				Log.i(TAG, "GetDataTask - current line: " + line);
				//dataProvider.addNewEntry(line);

			}
			rd.close();

		} catch (Exception e) {
			Log.e(TAG, "GetDataTask with Exception: " + e.getMessage());
		}
		return null;
	}



    protected void onPreExecute() {
    }

    protected void onProgressUpdate(Void... values) {
    }

}
