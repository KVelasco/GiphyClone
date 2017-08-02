package com.example.kevin.giphy.util;


import android.support.v7.util.DiffUtil;

import com.example.kevin.giphy.model.GifResponse;

import java.util.List;

public class GiphySearchDiffCallback extends DiffUtil.Callback {

    private List<GifResponse> oldList;
    private List<GifResponse> newList;

    public GiphySearchDiffCallback(List<GifResponse> oldList, List<GifResponse> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        GifResponse oldContact = oldList.get(oldItemPosition);
        GifResponse newContact = newList.get(newItemPosition);
        return oldContact.equals(newContact);
    }
}
