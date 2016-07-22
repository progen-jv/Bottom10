package com.movies.bten.app;

import android.app.Application;

/**
 * Created by Progen on 22/7/16.
 */
public class AppMain extends Application {
    private static AppMain _appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static AppMain getInstance() {
        return _appInstance;
    }


}
