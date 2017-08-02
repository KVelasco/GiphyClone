package com.example.kevin.giphy.search.view;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.kevin.giphy.R;
import com.example.kevin.giphy.mvp.InjectedViewHolder;
import com.example.kevin.giphy.util.GiphyUtil;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;
import rm.com.longpresspopup.PopupInflaterListener;
import rm.com.longpresspopup.PopupStateListener;

public class GiphySearchViewHolder extends InjectedViewHolder implements PopupInflaterListener {

    @BindView(R.id.giphy_search_item_image_view)
    ImageView imageView;

    private LongPressPopup popup;
    private ImageView popupImageView;
    private ImageView share;
    private ImageView download;
    private GiphySearchAdapter.GifPopUpListener listener;
    private String url;

    public GiphySearchViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(final String url) {
        this.url = url;

        GiphyUtil.loadGif(itemView.getContext(), imageView, url);
        RxView.longClicks(itemView).subscribe(aVoid -> popup.showNow());

        popup = new LongPressPopupBuilder(itemView.getContext())
                .setTarget(itemView)
                .setPopupView(R.layout.giphy_search_popup_layout, this)
                .setDismissOnLongPressStop(false)
                .setDismissOnTouchOutside(true)
                .setDismissOnBackPressed(true)
                .setCancelTouchOnDragOutsideView(true)
                .setPopupListener(new PopupStateListener() {
                    @Override
                    public void onPopupShow(@Nullable String s) {
                        GiphyUtil.loadGif(itemView.getContext(), popupImageView, url);
                    }

                    @Override
                    public void onPopupDismiss(@Nullable String s) {
                        popup.unregister();
                    }
                })
                .setAnimationType(LongPressPopup.ANIMATION_TYPE_FROM_CENTER)
                .build();
    }

    @Override
    public void onViewInflated(@Nullable String s, View view) {
        popupImageView = view.findViewById(R.id.giphy_search_popup_image_view);
        share = view.findViewById(R.id.giphy_search_popup_share);
        download = view.findViewById(R.id.giphy_search_popup_download);

        share.setOnClickListener(view13 -> {
            if (listener != null) {
                listener.onShare(GiphySearchViewHolder.this.url);
            }
        });

        download.setOnClickListener(view1 -> {
            if (listener != null) {
                listener.onDownload(GiphySearchViewHolder.this.url);
            }
        });
    }

    public void setListener(GiphySearchAdapter.GifPopUpListener listener) {
        this.listener = listener;
    }
}
