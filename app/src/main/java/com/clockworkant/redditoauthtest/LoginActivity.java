package com.clockworkant.redditoauthtest;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends AppCompatActivity {

    public static String REDIRECT_URI = "clockworkant://reddit.com/";

    private WebViewClient client;
    private WebView webView;

    private RedditApi redditApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        redditApi = new RedditApi();
        client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("authtest", String.format("webview loaded url %s", url));
                if (url.startsWith("clockworkant://")) {
                    String code = url.substring(url.indexOf("code=") + 5);
                    redditApi.getToken(code);
                }
                super.onPageFinished(view, url);
            }
        };
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(client);

        webView.loadUrl("https://www.reddit.com/api/v1/authorize.compact?client_id=_sjPbsZBiWsTDw&response_type=code&state=1&redirect_uri=" + REDIRECT_URI + "&scope=mysubreddits save read&duration=permanent");

    }

    public void getMulti(View view) {
        RedditOAuthApi.getInstance().getMultiReddits();
    }

}
