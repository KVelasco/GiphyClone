package com.example.kevin.giphy.mvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class InjectedViewHolder extends RecyclerView.ViewHolder{

    public InjectedViewHolder(View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
