package com.tugasmobile.searchmovieapplication.notification;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {
    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovie(@Query("api_key") String apiKey);
}
