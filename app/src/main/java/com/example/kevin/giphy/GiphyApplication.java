package com.example.kevin.giphy;


import android.app.Application;

import com.example.kevin.giphy.injection.components.ApplicationComponent;
import com.example.kevin.giphy.injection.components.DaggerApplicationComponent;
import com.example.kevin.giphy.injection.modules.ApplicationModule;
import com.example.kevin.giphy.injection.modules.NetworkModule;

public class GiphyApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("https://api.giphy.com/"))
                .build();
        applicationComponent.inject(this);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
