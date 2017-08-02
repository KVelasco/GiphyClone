package com.example.kevin.giphy.injection.modules;


import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    public Context providesContext() {
        return activity;
    }
}