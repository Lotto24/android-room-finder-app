package com;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;
import timber.log.Timber;

public class RoomFinderApplication extends Application {

    private ObjectGraph objectGraph;

    public static RoomFinderApplication get(Context applicationContext) {

        return (RoomFinderApplication) applicationContext;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        objectGraph = ObjectGraph.create(new RoomFinderModule(this));
        objectGraph.inject(this);
    }

    public void inject(Object object) {

        objectGraph.inject(object);
    }

}
