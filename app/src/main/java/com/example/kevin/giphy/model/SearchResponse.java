package com.example.kevin.giphy.model;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class SearchResponse implements Parcelable {


    @SerializedName("data") public abstract List<GifResponse> getData();
    @Nullable @SerializedName("meta") public abstract Meta getMeta();
    @Nullable @SerializedName("pagination") public abstract Pagination getPagination();

    public static Builder builder() {
        return new AutoValue_SearchResponse.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setData(List<GifResponse> newData);

        public abstract Builder setMeta(Meta newMeta);

        public abstract Builder setPagination(Pagination newPagination);

        public abstract SearchResponse build();
    }

    public static TypeAdapter<SearchResponse> typeAdapter(Gson gson) {
        return new AutoValue_SearchResponse.GsonTypeAdapter(gson);
    }
}
