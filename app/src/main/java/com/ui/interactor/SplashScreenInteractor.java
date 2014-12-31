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

package com.ui.interactor;

import android.app.Activity;

import com.data.DataProvider;
import com.data.LoginService;
import com.data.RetrieveDataTask;


import timber.log.Timber;

public class SplashScreenInteractor {

    RetrieveDataTask retrieveDataTask;
    boolean isUpdateAvailable = false;

    public SplashScreenInteractor(RetrieveDataTask retrieveDataTask) {
        this.retrieveDataTask = retrieveDataTask;
    }

    public boolean login(String password, Activity activity) {

        Timber.d("Button login pressed");
        LoginService loginService = new LoginService();

        if (loginService.checkPassword(password, activity)) {

            if (isUpdateAvailable) {
                Timber.d("Trigger update.");
                retrieveDataTask.execute(activity, password);
            }
            return true;
        }

        return false;

    }

    public boolean dataUpdateIsAvailable(Activity activity, boolean updateAvailable) {
        isUpdateAvailable = updateAvailable;
        LoginService login = new LoginService();
        boolean isLocalDataAvailable = new DataProvider().isDataStored(activity);
        boolean isLoggedIn = login.isLogin(activity);

        if (updateAvailable) {
            return true;
        } else {
            if (isLocalDataAvailable) {
                if (isLoggedIn) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }


}
