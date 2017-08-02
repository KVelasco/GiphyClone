package com.example.kevin.giphy.util;


import android.view.View;

import com.example.kevin.giphy.mvp.InjectedViewHolder;

public class FooterViewHolder extends InjectedViewHolder {

    private LoadingFooterView loadingFooterView;

    public FooterViewHolder(View itemView) {
        super(itemView);
        this.loadingFooterView = (LoadingFooterView) itemView;
    }

    public void bind(boolean isError) {
        loadingFooterView.setError(isError);
        if (isError){
            loadingFooterView.showError();
        } else {
            loadingFooterView.showProgressBar();
        }
    }
}
