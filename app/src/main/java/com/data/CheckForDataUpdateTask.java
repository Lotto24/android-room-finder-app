package com.data;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.Constants;
import com.events.DataUpdateAvailableEvent;
import com.squareup.otto.Bus;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by fbaue on 12/30/14.
 */
public class CheckForDataUpdateTask extends AsyncTask {

    private Bus bus;

    public CheckForDataUpdateTask(Bus bus) {
        this.bus = bus;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpParams httpParams = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);

        BufferedReader in = null;
        String line = "";
        try {
            HttpResponse response = httpClient.execute(new HttpGet(Constants.DATA_VERSION_URL));
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            line = in.readLine();
        } catch (IOException e) {
            return false;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    return false;
                }
            }
        }

        int remoteVersion;
        if (line.matches("^DataVersion:\\d+$")) {
            remoteVersion = Integer.parseInt(line.split(":")[1].trim());
        } else {
            return false;
        }
        SharedPreferences spData = ((Activity) params[0]).getApplicationContext().getSharedPreferences("Data", 0);
        int localVersion = spData.getInt("DataVersion", 0);
        if (remoteVersion > localVersion) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        bus.post(new DataUpdateAvailableEvent((Boolean) result));
    }
}
