package dev.buckybackend.oauth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OAuthUserResult {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;

    @Getter @Setter
    static class KakaoAccount {
        private Profile profile;
        private boolean has_email;
        private String email;
    }

    @Getter @Setter
    static class Profile {
        private String nickname;
        private String thumbnail_image_url;
        private String profile_image_url;
    }
}
