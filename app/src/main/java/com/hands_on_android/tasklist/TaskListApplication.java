package com.hands_on_android.tasklist;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.hands_on_android.tasklist.database.TaskListDBHelper;

public class TaskListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TaskListDBHelper.initialize(getApplicationContext());
        Stetho.initializeWithDefaults(this);
    }
}

