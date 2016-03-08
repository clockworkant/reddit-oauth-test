package com.clockworkant.redditoauthtest;

import com.google.gson.annotations.SerializedName;

public class AuthResponseModel {
    @SerializedName("access_token")
    public final String accessToken;
    @SerializedName("token_type")
    public final String tokenType;
    @SerializedName("expires_in")
    public final String expiresIn;
    public final String scope;
    public final String state;

    public AuthResponseModel(String accessToken, String tokenType, String expiresIn, String scope, String state) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.state = state;
    }
}
