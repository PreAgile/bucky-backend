package dev.buckybackend.oauth;

import dev.buckybackend.domain.Role;
import dev.buckybackend.domain.User;
import dev.buckybackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final OAuth2KakaoService oAuth2KakaoService;

    @GetMapping("/api/oauth2/kakao")
    public LoginDto oAuth2Kakao(@RequestParam String code,
                                @RequestParam(required = false) String redirect_url) {
        OAuthTokenResult tokenResult = oAuth2KakaoService.getToken(code, redirect_url);
        OAuthUserResult userResult = oAuth2KakaoService.getUserInfo(tokenResult);

        OAuthUserResult.KakaoAccount kakaoAccount = userResult.getKakao_account();
        OAuthUserResult.Profile profile = kakaoAccount.getProfile();

        User user = new User();
        user.setId(userResult.getId());
        user.setName(profile.getNickname());
        user.setEmail(kakaoAccount.getEmail());
        user.setRole(Role.MANAGER);

        userService.saveUser(user);

        return new LoginDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                profile.getThumbnail_image_url(),
                profile.getProfile_image_url()
        );
    }

    @Data
    @AllArgsConstructor
    static class LoginDto {
        private Long user_id;
        private String name;
        private String email;
        private Role role;
        private String thumbnail_image_url;
        private String profile_image_url;
    }

    @Data
    static class RedirectUrlRequest {
        private String redirect_url;
    }
}
