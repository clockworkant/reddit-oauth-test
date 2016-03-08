package com.clockworkant.redditoauthtest.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class RedditSharedPreferences implements RedditPreferences {

    private static final String PREF_ACCESS_TOKEN = "prefs.accesstoken";
    private final SharedPreferences sharedPreferences;
    private final BehaviorSubject<String> accessTokenObservable;

    public RedditSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("reddit.prefs", Context.MODE_PRIVATE);
        accessTokenObservable = BehaviorSubject.create(getAccessTokenFromSharedPrefs());
    }

    private String getAccessTokenFromSharedPrefs() {
        return sharedPreferences.getString(PREF_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString(PREF_ACCESS_TOKEN, accessToken).apply();
        accessTokenObservable.onNext(accessToken);
    }

    @Override
    public Observable<String> getAccessToken() {
        return accessTokenObservable;
    }
}
