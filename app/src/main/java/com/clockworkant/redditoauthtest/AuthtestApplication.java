package com.clockworkant.redditoauthtest;

import android.app.Application;

import com.clockworkant.redditoauthtest.prefs.RedditPreferences;
import com.clockworkant.redditoauthtest.prefs.RedditSharedPreferences;

public class AuthtestApplication extends Application {

    private RedditPreferences preferences;
    private static AuthtestApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = new RedditSharedPreferences(this);
        app = this;
    }

    public RedditPreferences getPreferences() {
        return preferences;
    }

    public static AuthtestApplication getInstance() {
        return app;
    }
}
