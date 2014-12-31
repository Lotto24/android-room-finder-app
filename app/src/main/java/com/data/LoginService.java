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

package com.data;

import android.app.Activity;
import android.content.SharedPreferences;

import com.Constants;

import timber.log.Timber;

public class LoginService {

    public boolean isLogin(Activity activity) {

        SharedPreferences sp_login = activity.getApplicationContext().getSharedPreferences("LOGIN", 0);
        boolean bool = sp_login.getBoolean("login", false);
        Timber.d("Login " + bool);
        return bool;
    }

    public boolean checkPassword(String password, Activity activity) {

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
