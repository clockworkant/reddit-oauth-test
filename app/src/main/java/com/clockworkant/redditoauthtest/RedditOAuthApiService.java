package com.clockworkant.redditoauthtest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RedditOAuthApiService {
    @GET("/api/multi/{multipath}")
    Call<ResponseBody> getReddit(@Path("multipath") String  multipath);

    @GET("/api/multi/mine")
    Call<ResponseBody> getMyMultiReddits();
}
