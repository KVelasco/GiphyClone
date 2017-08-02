package com.example.kevin.giphy.search.view;

import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kevin.giphy.R;
import com.example.kevin.giphy.model.GifResponse;
import com.example.kevin.giphy.model.ImageType;
import com.example.kevin.giphy.util.HeaderViewHolder;
import com.example.kevin.giphy.util.FooterViewHolder;
import com.example.kevin.giphy.util.GiphySearchDiffCallback;
import com.example.kevin.giphy.util.HeaderFooterRecyclerViewAdapter;
import com.example.kevin.giphy.util.LoadingFooterView;

import java.util.ArrayList;
import java.util.List;


public class GiphySearchAdapter extends HeaderFooterRecyclerViewAdapter<
        GiphySearchViewHolder,
        HeaderViewHolder,
        FooterViewHolder> {

    interface Listener {
        void onClick();
    }

    public interface GifPopUpListener {
        void onShare(String url);
        void onDownload(String url);
    }



    private final List<GifResponse> gifResponses = new ArrayList<>();
    private final List<View> headerViews = new ArrayList<>();
    private final List<View> footerViews = new ArrayList<>();
    private Listener footerListener;
    private GifPopUpListener gifPopUpListener;

    @Override
    protected int getHeaderItemCount() {
        return headerViews.size();
    }

    @Override
    protected int getFooterItemCount() {
        return footerViews.size();
    }

    @Override
    protected int getContentItemCount() {
        return gifResponses.size();
    }

    @Override
    protected HeaderViewHolder onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return new HeaderViewHolder(this.headerViews.get(headerViewType));
    }

    @Override
    protected FooterViewHolder onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return new FooterViewHolder(this.footerViews.get(footerViewType));
    }

    @Override
    protected GiphySearchViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giphy_search_item_view, parent, false);
        return new GiphySearchViewHolder(view);
    }

    @Override
    protected void onBindHeaderItemViewHolder(HeaderViewHolder headerViewHolder, int position) {

    }


    @Override
    protected void onBindFooterItemViewHolder(FooterViewHolder footerViewHolder, int position) {
        footerViewHolder.bind(((LoadingFooterView)this.footerViews.get(position)).isError());
        footerViewHolder.itemView.setOnClickListener(view -> {
            if (footerListener != null){
                footerListener.onClick();
            }
        });
    }

    @Override
    protected void onBindContentItemViewHolder(GiphySearchViewHolder giphySearchViewHolder, int position) {
        GifResponse gifResponse = this.gifResponses.get(position);
        giphySearchViewHolder.setListener(gifPopUpListener);
        giphySearchViewHolder.bind(gifResponse.getImages().get(ImageType.FIXED_HEIGHT.getValue()).getUrl());
    }


    public void setData(List<GifResponse> gifResponses) {
        final GiphySearchDiffCallback diffCallback = new GiphySearchDiffCallback(this.gifResponses, gifResponses);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.gifResponses.clear();
        this.gifResponses.addAll(gifResponses);

        diffResult.dispatchUpdatesTo(this); // calls adapter's notify methods after diff is computed
    }

    public void appendData(List<GifResponse> gifResponses) {
        int currentSize = getItemCount();
        this.gifResponses.addAll(gifResponses);
        notifyItemRangeInserted(currentSize, this.gifResponses.size() - 1);
    }

    public void addFooterView(View footer){
        this.footerViews.add(footer);
        notifyItemInserted(getHeaderItemCount() + getContentItemCount());
    }

    public void removeFooterViews(){
        if (this.footerViews.size() >= 1) {
            this.footerViews.remove(0);
            notifyItemRemoved(getHeaderItemCount() + getContentItemCount());
        }
    }

    public LoadingFooterView getFooterView(){
        LoadingFooterView view = null;
        if (this.footerViews.size() >= 1) {
            view = (LoadingFooterView) this.footerViews.get(0);
        }
        return view;
    }


    public void setFooterListener(Listener footerListener) {
        this.footerListener = footerListener;
    }

    public void setGifPopUpListener(GifPopUpListener gifPopUpListener) {
        this.gifPopUpListener = gifPopUpListener;
    }
}
