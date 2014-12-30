package com.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.data.CheckForDataUpdateTask;
import com.data.DataProvider;
import com.data.Login;
import com.data.RetrieveDataTask;
import com.events.DataUpdateAvailableEvent;
import com.events.DataUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.utils.RoomFinderApplication;

import javax.inject.Inject;

import timber.log.Timber;

public class SplashScreenActivity extends Activity implements View.OnClickListener {

    @Inject
    RetrieveDataTask retrieveDataTask;

    @Inject
    CheckForDataUpdateTask checkForDataUpdateTask;

    @Inject
    Bus eventsBus;

    private boolean isUpdateAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoomFinderApplication.get(getApplicationContext()).inject(this);
        setContentView(R.layout.splash_screen);
        Timber.d("Start splash screen.");

        checkForDataUpdateTask.execute(this);
    }

    @Subscribe
    public void dataWasUpdated(DataUpdatedEvent event) {
        Timber.d("Update was done successfully ? = " + event.isSuccessFull() + ", Start main activity");
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        startNextActivity();
    }

    @Subscribe
    public void dataUpdateIsAvailable(DataUpdateAvailableEvent event) {
        Login login = new Login();
        TextView textView = (TextView) findViewById(R.id.load);

        boolean isLocalDataAvailable = new DataProvider().isDataStored(this);
        boolean isUpdateAvailable = event.updateAvailable;
        boolean isLoggedIn = login.isLogin(this);

        if (isUpdateAvailable) {
            this.isUpdateAvailable = true;
            requestPassword();
        } else {
            this.isUpdateAvailable = false;
            if (isLocalDataAvailable) {
                if (isLoggedIn) {
                    textView.setText("Loading...");
                    startNextActivity();
                } else {
                    requestPassword();
                }
            } else {
                textView.setText("This service is currently not available");
            }
        }
    }

    private void requestPassword() {
        TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        TextView buttonPassword = (TextView) findViewById(R.id.loginButton);
        TextView enterTextViewPassword = (TextView) findViewById(R.id.loginEditText);
        headlinePassword.setVisibility(View.VISIBLE);
        buttonPassword.setVisibility(View.VISIBLE);
        enterTextViewPassword.setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.load);
        textView.setText("Waiting for Password ...");
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(enterTextViewPassword.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(buttonPassword.getWindowToken(), 0);
        buttonPassword.setOnClickListener(this);
    }

    private void startNextActivity() {
        Intent i = new Intent(SplashScreenActivity.this,
                RoomActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("register at event bus");
        eventsBus.register(this);
    }

    @Override
    public void onPause() {
        super.onStop();
        Timber.d("unregister at event bus");
        eventsBus.unregister(this);
    }

    @Override
    public void onClick(View v) {
        Login login = new Login();

        switch (v.getId()) {
            case R.id.loginButton:
                Timber.d("Button login pressed");
                TextView passwordEditText = (TextView) findViewById(R.id.loginEditText);
                String password = passwordEditText.getText().toString();

                if (login.checkPassword(password, this)) {
                    TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
                    TextView textView = (TextView) findViewById(R.id.load);
                    textView.setText("Loading...");
                    headlinePassword.setText("Password accepted - loading ...");
                    if (isUpdateAvailable) {
                        Timber.d("Trigger update.");
                        retrieveDataTask.execute(this, password);
                    }
                } else {
                    TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
                    headlinePassword.setText("Password not accepted !!!");
                }

                break;
            default:
                break;
        }
    }
}
