package com.example.kevin.giphy.injection;

import android.support.annotation.Nullable;

import com.example.kevin.giphy.GiphyApplication;
import com.example.kevin.giphy.search.injection.DaggerGiphySearchActivityComponent;
import com.example.kevin.giphy.search.injection.GiphySearchActivityComponent;

public class ComponentFactory {

    @Nullable
    private static GiphySearchActivityComponent giphySearchActivityComponent;

    private ComponentFactory() {
        throw new AssertionError();
    }

    public static GiphySearchActivityComponent getGiphySearchActivityComponent() {
        if (giphySearchActivityComponent != null) {
            return giphySearchActivityComponent;
        } else {
            return DaggerGiphySearchActivityComponent.builder()
                    .applicationComponent(GiphyApplication.getApplicationComponent())
                    .build();
        }
    }
}
