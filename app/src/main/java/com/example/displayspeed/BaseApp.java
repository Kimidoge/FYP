package com.example.displayspeed;

import android.app.Application;

public class BaseApp extends Application {

        private static BaseApp mInstance ;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static Application getApp(){
        return mInstance;
    }
}
