package com.example.kevin.giphy.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

@AutoValue
public abstract class GifResponse implements Parcelable {

    @SerializedName("type") public abstract String getType();
    @SerializedName("id") public abstract String getId();
    @SerializedName("slug") public abstract String getSlug();
    @SerializedName("url") public abstract String getUrl();
    @SerializedName("bitly_gif_url") public abstract String getBittlyGifUrl();
    @SerializedName("bitly_url") public abstract String getBittlyUrl();
    @SerializedName("embed_url") public abstract String getEmbedUrl();
    @SerializedName("username") public abstract String getUsername();
    @SerializedName("source") public abstract String getSource();
    @SerializedName("rating") public abstract String getRating();
    @Nullable @SerializedName("caption") public abstract String getCaption();
    @SerializedName("content_url") public abstract String getContentUrl();
    @SerializedName("source_tld") public abstract String getSpurceTld();
    @SerializedName("source_post_url") public abstract String getSourcePostUrl();
    @SerializedName("import_datetime") public abstract String getImportDateTime();
    @SerializedName("trending_datetime") public abstract String getTrendingDateTime();
    @SerializedName("images") public abstract Map<String, ImageResponse> getImages();

    public static Builder builder() {
        return new AutoValue_GifResponse.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setType(String newType);

        public abstract Builder setId(String newId);

        public abstract Builder setSlug(String newSlug);

        public abstract Builder setUrl(String newUrl);

        public abstract Builder setBittlyGifUrl(String newBittlyGifUrl);

        public abstract Builder setBittlyUrl(String newBittlyUrl);

        public abstract Builder setEmbedUrl(String newEmbedUrl);

        public abstract Builder setUsername(String newUsername);

        public abstract Builder setSource(String newSource);

        public abstract Builder setRating(String newRating);

        public abstract Builder setCaption(String newCaption);

        public abstract Builder setContentUrl(String newContentUrl);

        public abstract Builder setSpurceTld(String newSpurceTld);

        public abstract Builder setSourcePostUrl(String newSourcePostUrl);

        public abstract Builder setImportDateTime(String newImportDateTime);

        public abstract Builder setTrendingDateTime(String newTrendingDateTime);

        public abstract Builder setImages(Map<String, ImageResponse> newImages);

        public abstract GifResponse build();
    }

    public static TypeAdapter<GifResponse> typeAdapter(Gson gson) {
        return new AutoValue_GifResponse.GsonTypeAdapter(gson);
    }
}
