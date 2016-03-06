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

class RedditApi {
    private RedditApiService service = null;

    void getToken(String code) {
        Call<ResponseBody> authorization_code = getService().getToken(
                "authorization_code",
                code,
                LoginActivity.REDIRECT_URI);
        authorization_code.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("reddit", "" + response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("reddit", "Error getting token", t);
            }
        });
    }

    private RedditApiService getService() {
        if (service == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Authorization", "Basic X3NqUGJzWkJpV3NURHc6")
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(interceptor)
                    .build();
            Retrofit build = new Retrofit.Builder().client(client).baseUrl("https://www.reddit.com").build();
            service = build.create(RedditApiService.class);
        }
        return service;
    }
}
