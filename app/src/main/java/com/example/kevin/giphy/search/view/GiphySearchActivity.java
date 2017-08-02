package com.example.kevin.giphy.search.view;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.kevin.giphy.BuildConfig;
import com.example.kevin.giphy.R;
import com.example.kevin.giphy.injection.ComponentFactory;
import com.example.kevin.giphy.model.GifResponse;
import com.example.kevin.giphy.mvp.BaseActivity;
import com.example.kevin.giphy.search.injection.GiphySearchActivityComponent;
import com.example.kevin.giphy.search.presenter.GiphySearchPresenter;
import com.example.kevin.giphy.util.EndlessRecyclerViewScrollListener;
import com.example.kevin.giphy.util.GiphyUtil;
import com.example.kevin.giphy.util.LoadingFooterView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class GiphySearchActivity extends BaseActivity<
        GiphySearchActivityComponent,
        GiphySearchView,
        GiphySearchPresenter,
        GiphySearchActivityViewState> implements GiphySearchView  {

    private static final int RECYCLER_VIEW_SPAN_COUNT = 2;
    private static final int RECYCLER_VIEW_HEADER_SPAN_SIZE = 2;
    private static final int RECYCLER_VIEW_FOOTER_SPAN_SIZE = 2;
    private static final int RECYCLER_VIEW_CONTENT_SPAN_SIZE = 1;

    private static final String API_KEY = BuildConfig.GIPHY_API_KEY;

    private static final int OFFSET = 0;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.giphy_search_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.giphy_search_error_view_container)
    LinearLayout errorContainer;

    @BindView(R.id.giphy_search_empty_view_container)
    LinearLayout emptyContainer;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    GiphySearchAdapter giphySearchAdapter;

    EditText searchView;

    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    RxPermissions rxPermissions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initRecyclerView();
        setupSearchView();
        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.giphy_search_activity_layout;
    }

    @Override
    protected GiphySearchActivityComponent constructComponent() {
        return ComponentFactory.getGiphySearchActivityComponent();
    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.GONE);
        if (swipeRefreshLayout.isRefreshing()) {
            progressBar.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        errorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFooterError() {
        giphySearchAdapter.getFooterView().setError(true);
        giphySearchAdapter.notifyItemChanged(giphySearchAdapter.getContentItemCount());
        giphySearchAdapter.setFooterListener(() -> {
            getPresenter().searchGiphy(API_KEY, getPresenter().getQuery() , giphySearchAdapter.getContentItemCount(), true);
        });

    }

    @Override
    public void showResult(List<GifResponse> gifResponses) {
        getViewState().saveGifs(gifResponses);
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        emptyContainer.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.VISIBLE);
        giphySearchAdapter.removeFooterViews();
        giphySearchAdapter.setData(gifResponses);
        endlessRecyclerViewScrollListener.resetState();
    }

    @Override
    public void showEmptyView() {
        progressBar.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        emptyContainer.setVisibility(View.VISIBLE);
        giphySearchAdapter.removeFooterViews();
        endlessRecyclerViewScrollListener.resetState();
    }

    @Override
    public void appendResults(List<GifResponse> gifResponses) {
        getViewState().addGifs(gifResponses);
        giphySearchAdapter.removeFooterViews();
        giphySearchAdapter.appendData(gifResponses);
    }

    @Override
    public void showFooterProgress() {
        recyclerView.post(() -> {
            LoadingFooterView footerView = giphySearchAdapter.getFooterView();
            if (footerView == null){
                footerView = new LoadingFooterView(GiphySearchActivity.this, false);
                giphySearchAdapter.addFooterView(footerView);
            } else {
                footerView.setError(false);
                giphySearchAdapter.notifyItemChanged(giphySearchAdapter.getContentItemCount());
            }
            giphySearchAdapter.setFooterListener(null);
        });
    }

    @Override
    public void displayQueryText(String query) {
        searchView.setText(query);
        getPresenter().setQuery(query);
    }

    @Override
    public void shareFile(File file) {
        GiphyUtil.shareGifImage(GiphySearchActivity.this, file);

    }

    @Override
    public void displaySnackbar(String message) {
        Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show();

    }

    private void initToolbar() {
        toolbar.inflateMenu(R.menu.toolbar_menu);
        searchView = (EditText) toolbar.getMenu().findItem(R.id.search).getActionView();
        searchView.setHint(R.string.search_hint);
        searchView.setBackgroundResource(android.R.color.transparent);
        searchView.setSingleLine(true);
        toolbar.setTitle(R.string.app_name);
        toolbar.getMenu().findItem(R.id.search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                searchView.setFocusable(true);
                searchView.requestFocusFromTouch();
                GiphyUtil.showSoftKeyboard(GiphySearchActivity.this);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                GiphyUtil.hideSoftKeyboard(GiphySearchActivity.this);
                return true;
            }
        });
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), RECYCLER_VIEW_SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (giphySearchAdapter.getHeaderItemCount() > 0 && position < giphySearchAdapter.getHeaderItemCount()) {
                    return RECYCLER_VIEW_HEADER_SPAN_SIZE;
                } else if (giphySearchAdapter.getContentItemCount() > 0 && position - giphySearchAdapter.getHeaderItemCount() < giphySearchAdapter.getContentItemCount()) {
                    return RECYCLER_VIEW_CONTENT_SPAN_SIZE;
                } else {
                    return RECYCLER_VIEW_FOOTER_SPAN_SIZE;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(giphySearchAdapter);

        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getPresenter().searchGiphy(API_KEY, getPresenter().getQuery() ,totalItemsCount, true);
            }
        };

        swipeRefreshLayout.setOnRefreshListener(() -> getPresenter().searchGiphy(API_KEY, getPresenter().getQuery() , OFFSET, false));
        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        recyclerView.setOnTouchListener((view, motionEvent) -> {
            GiphyUtil.hideSoftKeyboard(GiphySearchActivity.this);
            return false;
        });

        giphySearchAdapter.setGifPopUpListener(new GiphySearchAdapter.GifPopUpListener() {
            @Override
            public void onShare(String url) {
                getPresenter().downloadGif(url, GiphySearchActivity.this.getExternalCacheDir().getAbsolutePath(), true);
            }

            @Override
            public void onDownload(String url) {
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                getPresenter().downloadGif(url, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), false);
                            } else {
                                displaySnackbar(getString(R.string.permission_error));
                            }
                        });

            }
        });
    }

    private void setupSearchView() {
        RxTextView.textChanges(searchView)
                .filter(charSequence -> charSequence.toString().trim().length() > 1 && !charSequence.toString().trim().equals(getPresenter().getQuery()))
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .switchMap(charSequence -> Observable.just(charSequence.toString()))
                .subscribe(s -> {
                    getViewState().saveQuery(s);
                    getPresenter().searchGiphy(API_KEY, s, OFFSET, false);
                });
    }

    @Override
    protected void onFirstCreate() {
        getPresenter().searchGiphy(API_KEY, "Simpsons", OFFSET, false);
    }


    @OnClick(R.id.giphy_search_error_view_container)
    void retrySearch() {
        getPresenter().searchGiphy(API_KEY, getPresenter().getQuery() , OFFSET, false);
    }


}
