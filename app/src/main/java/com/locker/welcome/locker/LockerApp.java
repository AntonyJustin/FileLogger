package com.locker.welcome.locker;

import android.app.Application;
import android.os.Handler;

import java.util.concurrent.locks.Lock;

/**
 * Created by welcome on 30-May-20.
 */

public class LockerApp extends Application {
    private static LockerApp mApp;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        LockerApp.mApp = this;
        handler = new Handler();
    }

    public static LockerApp getApp() {
        return mApp;
    }

    public void runOnUiThread(final Runnable runnable) {
        handler.post(runnable);
    }
}
