package com.clockworkant.redditoauthtest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RedditApiService {
    @FormUrlEncoded
    @POST("api/v1/access_token")
    Call<AuthResponseModel> getToken(
            @Field("grant_type") String granttype,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri);
}
