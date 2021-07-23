package com.g.dummy.service;

import com.g.dummy.controller.ApiController;
import com.nimbusds.jose.shaded.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    public boolean hasPermission(Authentication authentication, String permission) {
        boolean hasPermission = false;
        try {
            JSONArray permissions = (JSONArray) ((Jwt) authentication.getCredentials()).getClaims().get("permissions");
            hasPermission = permissions.contains(permission);
            return hasPermission;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("{} hasPermission for {}: {}",authentication.getName(), permission, hasPermission);
        }
        return false;
    }
}
