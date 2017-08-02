package com.example.kevin.giphy.util;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.kevin.giphy.R;


public class LoadingFooterView extends FrameLayout {

    private ProgressBar progressBar;
    private LinearLayout errorContainer;
    private boolean isError;



    public LoadingFooterView(@NonNull Context context, boolean isError) {
        super(context);
        init(context);
        this.isError = isError;
    }

    public LoadingFooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooterView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.footer_loading_view, this, true);

        progressBar = findViewById(R.id.footer_loading_view_progress_bar);
        errorContainer = findViewById(R.id.footer_loading_view_error_view_container);
    }

    public void showProgressBar(){
        errorContainer.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    public void showError(){
        progressBar.setVisibility(GONE);
        errorContainer.setVisibility(VISIBLE);
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
