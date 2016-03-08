package com.clockworkant.redditoauthtest;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RedditOAuthApi {
    private RedditOAuthApiService service;
    private String bearerToken;
    private static RedditOAuthApi redditOAuthApi;
    private RedditOAuthApi() {
        //no instance
    }

    public static RedditOAuthApi getInstance(){
        if(redditOAuthApi == null) {
            redditOAuthApi = new RedditOAuthApi();
        }

        return redditOAuthApi;
    }

    private RedditOAuthApiService getService() {
        if (service == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", "bearer " + getBearerToken())
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(interceptor)
                    .build();
            Retrofit build = new Retrofit.Builder().client(client).baseUrl("https://oauth.reddit.com").build();
            service = build.create(RedditOAuthApiService.class);
        }
        return service;
    }

    private String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public void getMultiReddits() {
        Call<ResponseBody> bodyCall = getService().getMyMultiReddits();
        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("", "onResponse: ");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getImagesReddit(){
        getService().getReddit("/user/DarkFlare/m/images")
    }
}
