package com.g.dummy.service;


import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class UserService {

    @Value("${baseurl:https://jgarbora.eu.auth0.com}")
    private String baseUrl;

    @Value("{auth0.management.api.clientId}")
    private String clientId;

    @Value("{auth0.management.api.clientSecret}")
    private String clientSecret;

    private String oauthApiBearerToken;

    @Autowired
    private HttpServletRequest request;

    private RestTemplate restTemplate = new RestTemplate();

    public void init() {
        Map<String, String> body = new HashMap<>();
        body.put("client_id",clientId);
        body.put("client_secret",clientSecret);
        body.put("audience","https://jgarbora.eu.auth0.com/api/v2/");
        body.put("grant_type","client_credentials");

        ResponseEntity<LinkedHashMap> responseEntity = restTemplate.exchange(baseUrl+"/oauth/token", HttpMethod.POST, new HttpEntity<>(body, buildHeaders()), LinkedHashMap.class);
        oauthApiBearerToken = responseEntity.getBody().get("access_token").toString();
    }

    public ResponseEntity<LinkedHashMap> userinfo() {
        return restTemplate.exchange(baseUrl+"/userinfo", HttpMethod.POST, new HttpEntity<>("{}", buildHeaders()), LinkedHashMap.class);
    }


    public ResponseEntity<LinkedHashMap> findUserById() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // example https://jgarbora.eu.auth0.com/api/v2/users/auth0%7C60f879147ddc3f0069ecd7bf
        return restTemplate.exchange(String.format("%s/api/v2/users/%s",baseUrl,((Jwt) authentication.getCredentials()).getSubject()), HttpMethod.GET, new HttpEntity<>(buildHeadersForAuth0Api()), LinkedHashMap.class);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("%s", request.getHeader("authorization")));
        return headers;
    }

    private HttpHeaders buildHeadersForAuth0Api() {
        if (oauthApiBearerToken == null) {
            init();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", oauthApiBearerToken ));
        return headers;
    }
}
