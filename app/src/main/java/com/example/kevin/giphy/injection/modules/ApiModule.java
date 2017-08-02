package com.example.kevin.giphy.injection.modules;


import com.example.kevin.giphy.api.GiphyService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    public GiphyService giphyService(Retrofit retrofit) {
        return retrofit.create(GiphyService.class);
    }
}