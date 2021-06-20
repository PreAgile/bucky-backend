package dev.buckybackend.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OAuthTokenResult {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expires_in;
}
