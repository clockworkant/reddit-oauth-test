package com.clockworkant.redditoauthtest.prefs;

import rx.Observable;

public interface RedditPreferences {
    void setAccessToken(String accessToken);
    Observable<String> getAccessToken();
}
