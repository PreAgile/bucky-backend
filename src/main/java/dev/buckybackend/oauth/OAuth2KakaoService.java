package dev.buckybackend.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.buckybackend.common.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2KakaoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    /**
     * Access Token을 요청한다.
     * @param code
     * @return
     */
    public OAuthTokenResult getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", Constant.GRANT_TYPE);
        params.add("client_id", Constant.CLIENT_ID);
        params.add("client_secret", Constant.CLIENT_SECRET);
        params.add("redirect_uri", Constant.REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> oResponse = restTemplate.postForEntity(Constant.TOKEN_URI, request, String.class);
            OAuthTokenResult oResult = objectMapper.readValue(oResponse.getBody(), OAuthTokenResult.class);
            return oResult;
        } catch (RestClientException | JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * 유저 정보를 요청한다.
     * @param tokenResult
     * @return
     */
    public OAuthUserResult getUserInfo(OAuthTokenResult tokenResult) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String _Authorization = tokenResult.getAccess_token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenResult.getToken_type() + ' ' + _Authorization);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> oResponse = restTemplate.postForEntity(Constant.USER_INFO_URI, request, String.class);
            OAuthUserResult oResult = objectMapper.readValue(oResponse.getBody(), OAuthUserResult.class);
            return oResult;
        } catch (RestClientException | JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }
}
