package com.data;

import android.app.Activity;
import android.os.AsyncTask;

import com.events.DataUpdatedEvent;
import com.squareup.otto.Bus;
import com.utils.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Inject;

import timber.log.Timber;

public class RetrieveDataTask extends AsyncTask {

    private final Bus eventsBus;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Inject
    public RetrieveDataTask(Bus eventsBus) {
        this.eventsBus = eventsBus;
    }

    @Override
    protected void onPostExecute(Object result) {
        eventsBus.post(new DataUpdatedEvent((Boolean) result));
    }

    protected Boolean doInBackground(Object... params) {

        try {
            Timber.d("check for updates...");
            DataProvider dataProvider = new DataProvider();
            boolean isAlreadyStored = false;
            isAlreadyStored = dataProvider.isDataStored((Activity)params[0]);

            if (!isAlreadyStored) {
                Timber.d("no data available, so retrieve data...");
                HttpResponse response = new DefaultHttpClient().execute(new HttpGet(Constants.NAME_ROOM_URL));
                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                String line = "";

                while ((line = rd.readLine()) != null) {

                    dataProvider.addNewEntry(line, (Activity)params[0]);

                }
                rd.close();
            }
            Timber.d("... update success");
            return true;
        } catch (Exception e) {
            Timber.e(e, "... update failure " + e.getMessage());
        }
        return false;

    }

}

