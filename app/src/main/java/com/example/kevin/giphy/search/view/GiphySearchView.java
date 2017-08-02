package com.example.kevin.giphy.search.view;


import com.example.kevin.giphy.model.GifResponse;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.io.File;
import java.util.List;

public interface GiphySearchView extends MvpView {

    void showProgress();

    void showError();

    void showFooterError();

    void showFooterProgress();

    void showEmptyView();

    void appendResults(List<GifResponse> gifResponses);

    void showResult(List<GifResponse> gifResponses);

    void displayQueryText(String query);

    void shareFile(File file);

    void displaySnackbar(String message);
}
