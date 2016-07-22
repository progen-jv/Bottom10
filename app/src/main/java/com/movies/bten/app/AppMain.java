package com.movies.bten.app;

import android.app.Application;

import com.movies.bten.commons.util.AppLog;
import com.movies.bten.commons.util.DeviceInfo;
import com.movies.bten.commons.util.MessageUtil;

/**
 * Created by Progen on 22/7/16.
 */
public class AppMain extends Application {
    private static AppMain _appInstance;
    private boolean isLogEnabled = true;

    @Override
    public void onCreate() {
        super.onCreate();
        _appInstance = this;

        /* Initialize app tools */
        DeviceInfo.initializeDeviceInfo(getApplicationContext());
        AppLog.initializeAppLog(isLogEnabled);
        MessageUtil.initializeMesageUtil(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static AppMain getInstance() {
        return _appInstance;
    }


}
