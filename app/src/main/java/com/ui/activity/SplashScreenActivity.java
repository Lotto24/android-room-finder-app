/*
 * Copyright 2014 eSailors IT Solutions GmbH
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.RoomFinderApplication;
import com.data.CheckForDataUpdateTask;
import com.data.DataProvider;
import com.data.LoginService;
import com.data.RetrieveDataTask;
import com.events.DataUpdateAvailableEvent;
import com.events.DataUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.ui.interactor.SplashScreenInteractor;
import com.ui.presenter.SplashScreenPresenter;
import com.ui.view.SplashScreenView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *  Splash Screen Activity is shown if new data is retrieved.
 *  This class contains only logic for the manipulation of the concrete ui elements itself,
 *  but it has no business logic and ui changes are triggered by a presenter.
 *
 */
public class SplashScreenActivity extends Activity implements SplashScreenView {

    @Inject
    CheckForDataUpdateTask checkForDataUpdateTask;

    @Inject
    SplashScreenInteractor splashScreenInteractor;

    private SplashScreenPresenter presenter;

    @Inject
    Bus eventsBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoomFinderApplication.get(getApplicationContext()).inject(this);
        setContentView(R.layout.splash_screen);
        Timber.d("Start splash screen.");
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        presenter = new SplashScreenPresenter(splashScreenInteractor, this, eventsBus, this);
        checkForDataUpdateTask.execute(this);
        ButterKnife.inject(this);
    }

    public void showPasswordRequired() {
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
    }

    public void skipLogin() {
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        Intent i = new Intent(SplashScreenActivity.this,
                RoomActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("register at event bus");
        presenter.register();
    }

    @Override
    public void onPause() {
        super.onStop();
        Timber.d("unregister at event bus");
        presenter.unregister();
    }

    @OnClick(R.id.loginButton)
    public void onClick(View v) {

         TextView passwordEditText = (TextView) findViewById(R.id.loginEditText);
         String password = passwordEditText.getText().toString();
         presenter.login(password);

    }

    public void showPasswordAccepted() {
        TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        TextView textView = (TextView) findViewById(R.id.load);
        textView.setText("Loading...");
        headlinePassword.setText("Password accepted - loading ...");
    }

    public void showNotPasswordAccepted() {
        TextView headlinePassword = (TextView) findViewById(R.id.HeadlineLoginTextView);
        headlinePassword.setText("Password not accepted !!!");
    }
}
