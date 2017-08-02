package com.example.kevin.giphy.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kevin.giphy.R;

import java.io.File;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

public class GiphyUtil {


    public static ColorDrawable getRandomColorPlaceHolder() {
        Random random = new Random();
        int red = random.nextInt(255 + 1);
        int green = random.nextInt(255 + 1);
        int blue = random.nextInt(255 + 1);

        return new ColorDrawable(Color.rgb(red, green, blue));
    }

    public static void loadGif(Context context, ImageView view, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(GiphyUtil.getRandomColorPlaceHolder())
                .error(R.drawable.ic_error)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public static void shareGifImage(Activity activity, File file) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, "Share GIF"));
    }

    public static Function<Response<ResponseBody>, Single<File>> responseToFile(final String filepath) {
        return responseBodyResponse -> Single.create(source -> {
            try {
                if (responseBodyResponse.isSuccessful()) {
                    ResponseBody responseBody = responseBodyResponse.body();

                    String type = responseBody.contentType().subtype();
                    File file = new File(filepath, System.currentTimeMillis() + "." + type);

                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    sink.writeAll(responseBody.source());
                    sink.close();
                    source.onSuccess(file);
                } else {
                    source.onError(new Error(responseBodyResponse.message()));
                }
            } catch (Throwable error) {
                error.printStackTrace();
                source.onError(error);
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

}
