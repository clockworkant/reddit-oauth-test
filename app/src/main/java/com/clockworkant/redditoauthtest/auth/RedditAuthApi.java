package com.clockworkant.redditoauthtest.auth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

class RedditAuthApi {
    public static String REDIRECT_URI = "clockworkant://reddit.com/";

    private RedditAuthService service = null;
    private static RedditAuthApi redditAuthApi = null;

    private RedditAuthApi() {
    }

    Observable<String> getToken(String code) {
        return getService().getToken(
                "authorization_code",
                code,
                REDIRECT_URI)
                .flatMap(new Func1<AuthResponseModel, rx.Observable<String>>() {
                    @Override
                    public rx.Observable<String> call(AuthResponseModel authResponseModel) {
                        return Observable.just(authResponseModel.accessToken);
                    }
                });
    }

    private RedditAuthService getService() {
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
            Retrofit build = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .baseUrl("https://www.reddit.com")
                    .build();
            service = build.create(RedditAuthService.class);
        }
        return service;
    }

    public static RedditAuthApi getInstance() {
        if (redditAuthApi == null) {
            redditAuthApi = new RedditAuthApi();
        }
        return redditAuthApi;
    }
}
