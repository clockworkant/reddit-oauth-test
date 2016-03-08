package com.clockworkant.redditoauthtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.clockworkant.redditoauthtest.auth.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.text_access_token)
    TextView accessTokenLabel;

    @Bind(R.id.btn_login)
    Button loginButton;

    private RedditOAuthApi redditOAuthApi;

    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        redditOAuthApi = RedditOAuthApi.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscriptions = new CompositeSubscription();
        registerAccessTokenListener(subscriptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptions.unsubscribe();
    }

    private void registerAccessTokenListener(CompositeSubscription subscriptions) {
        subscriptions.add(
                AuthtestApplication.getInstance()
                        .getPreferences()
                        .getAccessToken()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String accessToken) {
                                boolean hasAccessToken = !TextUtils.isEmpty(accessToken);
                                accessTokenLabel.setVisibility(hasAccessToken ? View.VISIBLE : View.INVISIBLE);
                                loginButton.setEnabled(!hasAccessToken);
                            }
                        }));
    }

    @OnClick(R.id.btn_getImagesMultireddit)
    public void getMultiReddits() {
        redditOAuthApi.getMultiReddits();
    }

    @OnClick(R.id.btn_login)
    public void openLoginActivity() {
        startActivity(LoginActivity.getStartIntent(this));
    }

    @OnClick(R.id.btn_invalidate_access_token)
    public void invalidateAccessToken(){
        AuthtestApplication.getInstance().getPreferences().setAccessToken(null);
    }


}
