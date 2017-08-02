package com.example.kevin.giphy.injection.components;

import android.content.Context;

import com.example.kevin.giphy.GiphyApplication;
import com.example.kevin.giphy.api.GiphyService;
import com.example.kevin.giphy.injection.modules.ApplicationModule;
import com.example.kevin.giphy.injection.scopes.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;


@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    GiphyApplication application();

    OkHttpClient okHttpClient();

    GiphyService pylotService();

    void inject(GiphyApplication application);
}