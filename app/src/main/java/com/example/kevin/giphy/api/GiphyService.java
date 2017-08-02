package com.example.kevin.giphy.api;

import com.example.kevin.giphy.model.SearchResponse;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface GiphyService {

    @GET("v1/gifs/search")
    Single<Response<SearchResponse>> searchGiphy(@Query("api_key") String apiKey, @Query("q") String q,
                                                 @Query("offset") int offset, @Query("limit") int limit);

    @Streaming
    @GET
    Single<Response<ResponseBody>> downloadFileByUrl(@Url String fileUrl);
}
