package com.example.kevin.giphy.search.presenter;

import com.example.kevin.giphy.BuildConfig;
import com.example.kevin.giphy.api.GiphyService;
import com.example.kevin.giphy.search.view.GiphySearchView;
import com.example.kevin.giphy.util.GiphyUtil;
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GiphySearchPresenter extends MvpNullObjectBasePresenter<GiphySearchView> {

    private static final int LIMIT = 26;
    private final GiphyService giphyService;
    @Nullable private Disposable subscription;
    private String query;


    public GiphySearchPresenter(GiphyService giphyService) {
        this.giphyService = giphyService;
    }

    public void searchGiphy(String apiKey, String query, int offset, final boolean loadMore) {
        if (!loadMore) {
            getView().showProgress();
        } else {
            getView().showFooterProgress();
        }
        this.query = query;
        this.subscription = giphyService.searchGiphy(apiKey, query, offset, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResponseResponse -> {
                    if (searchResponseResponse.isSuccessful()) {
                        if (loadMore) {
                            getView().appendResults(searchResponseResponse.body().getData());
                        } else {
                            if (searchResponseResponse.body().getData().size() == 0) {
                                getView().showEmptyView();
                            } else {
                                getView().showResult(searchResponseResponse.body().getData());
                            }
                        }
                    } else {
                        if (loadMore) {
                            getView().showFooterError();
                        } else {
                            getView().showError();
                        }
                    }
                }, throwable -> {
                    if (loadMore) {
                        getView().showFooterError();
                    } else {
                        getView().showError();
                    }
                    if (BuildConfig.DEBUG) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void downloadGif(String url, String filePath, final boolean share) {
        this.giphyService.downloadFileByUrl(url)
                .flatMap(GiphyUtil.responseToFile(filePath))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    if (share) {
                        getView().shareFile(file);
                    } else {
                        getView().displaySnackbar("GIF Downloaded");
                    }
                }, throwable -> getView().displaySnackbar(throwable.getMessage()));
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance && subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
