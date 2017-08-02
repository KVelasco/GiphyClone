package com.example.kevin.giphy.search.injection;

import com.example.kevin.giphy.api.GiphyService;
import com.example.kevin.giphy.search.presenter.GiphySearchPresenter;
import com.example.kevin.giphy.search.view.GiphySearchActivityViewState;
import com.example.kevin.giphy.search.view.GiphySearchAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class GiphySearchActivityModule {

    @Provides
    public GiphySearchActivityViewState providesGiphySearchActivityViewState() {
        return new GiphySearchActivityViewState();
    }


    @Provides
    public GiphySearchPresenter providesGiphySearchPresenter(GiphyService giphyService) {
        return new GiphySearchPresenter(giphyService);
    }

    @Provides
    public GiphySearchAdapter providesGiphySearchAdapter() {
        return new GiphySearchAdapter();
    }
}
