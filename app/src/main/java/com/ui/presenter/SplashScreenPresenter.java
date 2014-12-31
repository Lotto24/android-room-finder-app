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
package com.ui.presenter;


import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.data.DataProvider;
import com.data.LoginService;
import com.events.DataUpdateAvailableEvent;
import com.events.DataUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.ui.activity.R;
import com.ui.interactor.SplashScreenInteractor;
import com.ui.view.SplashScreenView;

import timber.log.Timber;

public class SplashScreenPresenter {

    private SplashScreenInteractor splashInteractor;
    private SplashScreenView splashView;
    private Bus eventsBus;
    private Activity activity;

    public SplashScreenPresenter(SplashScreenInteractor splashInteractor, SplashScreenView splashView, Bus eventsBus, Activity activity) {
        this.splashInteractor = splashInteractor;
        this.splashView = splashView;
        this.eventsBus = eventsBus;
        this.activity = activity;
    }

    public void login(String password) {
        if(splashInteractor.login(password, activity)) {
            splashView.showPasswordAccepted();
        } else {
            splashView.showNotPasswordAccepted();
        }
    }

    @Subscribe
    public void dataUpdateIsAvailable(DataUpdateAvailableEvent event) {

        if(splashInteractor.dataUpdateIsAvailable(activity, event.updateAvailable)) {
            splashView.showPasswordRequired();
        } else {
            splashView.skipLogin();
        }
    }

    @Subscribe
    public void dataWasUpdated(DataUpdatedEvent event) {
        Timber.d("Update was done successfully ? = " + event.isSuccessFull() + ", Start main activity");
        splashView.skipLogin();
    }

    public void register() {
        eventsBus.register(this);
    }

    public void unregister() {
        eventsBus.unregister(this);
    }

}
