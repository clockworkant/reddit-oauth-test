package com.clockworkant.redditoauthtest.auth;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

interface RedditAuthService {
    @FormUrlEncoded
    @POST("api/v1/access_token")
    Observable<AuthResponseModel> getToken(
            @Field("grant_type") String granttype,
            @Field("code") String code,
            @Field("redirect_uri") String redirectUri);
}
