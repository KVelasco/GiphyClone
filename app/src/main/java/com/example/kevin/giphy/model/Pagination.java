package com.example.kevin.giphy.model;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Pagination implements Parcelable {

    @SerializedName("total_count") public abstract int getTotalCount();
    @SerializedName("count") public abstract int getCount();
    @SerializedName("offset") public abstract int getOffset();

    public static Builder builder() {
        return new AutoValue_Pagination.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTotalCount(int newTotalCount);

        public abstract Builder setCount(int newCount);

        public abstract Builder setOffset(int newOffset);

        public abstract Pagination build();
    }

    public static TypeAdapter<Pagination> typeAdapter(Gson gson) {
        return new AutoValue_Pagination.GsonTypeAdapter(gson);
    }
}
