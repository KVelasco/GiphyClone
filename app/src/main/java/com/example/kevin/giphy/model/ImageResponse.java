package com.example.kevin.giphy.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ImageResponse implements Parcelable {

    @Nullable @SerializedName("url") public abstract String getUrl();
    @Nullable @SerializedName("width") public abstract String getWidth();
    @Nullable @SerializedName("height") public abstract String getHeight();
    @Nullable @SerializedName("size") public abstract String getSize();
    @Nullable @SerializedName("frames") public abstract String getFrames();
    @Nullable @SerializedName("mp4") public abstract String getMp4();
    @Nullable @SerializedName("mp4_size") public abstract String getMp4Size();
    @Nullable @SerializedName("webp") public abstract String getWebp();
    @Nullable @SerializedName("webp_size") public abstract String getWebpSize();

    public static Builder builder() {
        return new AutoValue_ImageResponse.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setUrl(String newUrl);

        public abstract Builder setWidth(String newWidth);

        public abstract Builder setHeight(String newHeight);

        public abstract Builder setSize(String newSize);

        public abstract Builder setFrames(String newFrames);

        public abstract Builder setMp4(String newMp4);

        public abstract Builder setMp4Size(String newMp4Size);

        public abstract Builder setWebp(String newWebp);

        public abstract Builder setWebpSize(String newWebpSize);

        public abstract ImageResponse build();
    }

    public static TypeAdapter<ImageResponse> typeAdapter(Gson gson) {
        return new AutoValue_ImageResponse.GsonTypeAdapter(gson);
    }
}
