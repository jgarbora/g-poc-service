package com.g.dummy.service;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    public boolean hasPermission(Authentication authentication, String permission) {
        try {
            JSONArray permissions = (JSONArray) ((Jwt) authentication.getCredentials()).getClaims().get("permissions");
            return permissions.contains(permission);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
