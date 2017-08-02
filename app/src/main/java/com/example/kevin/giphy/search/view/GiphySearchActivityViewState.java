package com.example.kevin.giphy.search.view;

import android.support.annotation.Nullable;

import com.example.kevin.giphy.model.GifResponse;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import java.util.List;



public class GiphySearchActivityViewState implements ViewState<GiphySearchView> {

    @Nullable
    private List<GifResponse> gifResponses;

    @Nullable
    private String query;

    @Override
    public void apply(GiphySearchView view, boolean retained) {
        if (gifResponses != null) {
            view.showResult(gifResponses);
        }

        if (query != null) {
            view.displayQueryText(query);
        }
    }

    public void saveGifs(List<GifResponse> gifResponses) {
        this.gifResponses = gifResponses;
    }

    public void addGifs(List<GifResponse> gifResponses) {
        if (this.gifResponses != null){
            this.gifResponses.addAll(gifResponses);
        }
    }

    public void saveQuery(String query){
        this.query = query;
    }
}
