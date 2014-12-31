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

package com;

import android.app.Application;

import com.data.CheckForDataUpdateTask;
import com.data.RetrieveDataTask;
import com.squareup.otto.Bus;
import com.ui.activity.SplashScreenActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        complete = true,
        library = true,
        injects = { RoomFinderApplication.class, SplashScreenActivity.class, RetrieveDataTask.class }
)
public class RoomFinderModule {

    private final Application app;

    public RoomFinderModule(Application application) {
        this.app = application;
    }

    @Provides
    public RetrieveDataTask provideRetrieveDataTask(Bus eventsBus) {
        return new RetrieveDataTask(eventsBus);
    }

    @Provides
    public CheckForDataUpdateTask provideCheckForUpdateTask(Bus eventsBus) {
        return new CheckForDataUpdateTask(eventsBus);
    }

    @Provides
    @Singleton
    public Application provideRoomFinderApplication() {
        return app;
    }

    @Provides
    @Singleton
    public Bus provideEventsBus() {
        return new Bus();
    }

}
