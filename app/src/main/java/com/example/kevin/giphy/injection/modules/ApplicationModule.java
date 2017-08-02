package com.example.kevin.giphy.injection.modules;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.example.kevin.giphy.GiphyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class ApplicationModule {

    private GiphyApplication application;

    public ApplicationModule(GiphyApplication application) {
        this.application = application;
    }

    @Provides
    public GiphyApplication provideApplication() {
        return application;
    }

    @Provides
    public Context provideContext() {
        return application;
    }
}
