package com.clockworkant.redditoauthtest.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.clockworkant.redditoauthtest.AuthtestApplication;
import com.clockworkant.redditoauthtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    @Bind(R.id.webview)
    WebView webView;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("authtest", String.format("webview loaded url %s", url));
                if (url.startsWith("clockworkant://")) {
                    String code = url.substring(url.indexOf("code=") + 5);
                    retrieveAccessCode(code);
                }
                super.onPageFinished(view, url);
            }
        };
        webView.setWebViewClient(webViewClient);

        webView.loadUrl("https://www.reddit.com/api/v1/authorize.compact?client_id=_sjPbsZBiWsTDw&response_type=code&state=1&redirect_uri=" + RedditAuthApi.REDIRECT_URI + "&scope=mysubreddits save read&duration=permanent");

    }

    private void retrieveAccessCode(String code) {
        RedditAuthApi.getInstance()
                .getToken(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String accessCode) {
                        getActivity().finish();
                        AuthtestApplication.getInstance()
                                .getPreferences()
                                .setAccessToken(accessCode);
                    }
                });
    }

}
