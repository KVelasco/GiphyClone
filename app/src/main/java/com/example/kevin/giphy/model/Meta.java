package com.example.kevin.giphy.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Meta implements Parcelable {

    @SerializedName("status") public abstract int getStatus();
    @SerializedName("msg") public abstract String getMsg();


    public static Builder builder() {
        return new AutoValue_Meta.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setStatus(int newStatus);

        public abstract Builder setMsg(String newMsg);

        public abstract Meta build();
    }


    public static TypeAdapter<Meta> typeAdapter(Gson gson) {
        return new AutoValue_Meta.GsonTypeAdapter(gson);
    }

}
