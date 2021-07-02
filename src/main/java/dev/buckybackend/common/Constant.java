package dev.buckybackend.common;

public class Constant {

    public static final String CLIENT_ID = "930c96be7c2e9e9062b2a584788fe333";
    public static final String CLIENT_SECRET = "tcGJFLIYJNAwSSFsfMCqrzMzmDd6W8lj";
    public static final String REDIRECT_URI = "https://www.bucky.co.kr/oauth/kakao"; //front
//    public static final String REDIRECT_URI = "http://localhost:5000/api/oauth2/kakao"; //test
    public static final String GRANT_TYPE = "authorization_code";

    public static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    public static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public static final String LOGIN_URI = "https://kauth.kakao.com/oauth/authorize?client_id=930c96be7c2e9e9062b2a584788fe333&redirect_uri=http%3A%2F%2Flocalhost%3A5000%2Fapi%2Foauth2%2Fkakao&response_type=code";
    public static final String AGREEMENT_URI = "https://kauth.kakao.com/oauth/authorize?client_id=930c96be7c2e9e9062b2a584788fe333&redirect_uri=http%3A%2F%2Flocalhost%3A5000%2Fapi%2Foauth2%2Fkakao&response_type=code&scope=account_email";

    //Constant
    public static final String STUDIO_LIST_SIZE = "16";
    public static final String IMAGE_LIST_SIZE = "30";
}
