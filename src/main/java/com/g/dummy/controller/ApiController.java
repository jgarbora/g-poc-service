package com.g.dummy.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.g.dummy.dto.ResponseWrapper;
import com.g.dummy.service.SecurityService;
import com.g.dummy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/public")
    public ResponseEntity<?> publicEndpoint(@RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public ResponseEntity<?> privateEndpoint(@RequestHeader Map<String, String> headers) {
        print(headers);
        return ResponseEntity.ok(new ResponseWrapper("All good. You can see this because you are Authenticated."));
    }

    @PostMapping(value = "/admin/user")
    //@PreAuthorize("myHasPermission('create:user')")
    @PreAuthorize("@securityService.hasPermission(authentication,'create:user')")
    public ResponseEntity privateAdminScoped(@RequestHeader Map<String, String> headers) {
        print(headers);
        return ResponseEntity.ok(new ResponseWrapper("All good. You can see this because you are Authenticated with a Token granted the 'create:user' scope"));

    }

    @PostMapping(value = "/user/interrogation")
    //@PreAuthorize("hasAuthority('create:interrogation')")
    @PreAuthorize("@securityService.hasPermission(authentication,'create:interrogation')")
    public ResponseEntity privateUserScoped(@RequestHeader Map<String, String> headers) {
        print(headers);
        return ResponseEntity.ok(new ResponseWrapper("All good. You can see this because you are Authenticated with a Token granted the 'create:interrogation' scope"));
    }

    @GetMapping(value = "/get-authentication")
    public ResponseEntity getAuthentication() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping(value = "/find-user")
    public ResponseEntity metadata() {
        return ResponseEntity.ok(userService.findUserById());
    }

    private void print(Map<String, String> headers) {
        try {
            DecodedJWT jwt = JWT.decode(headers.get("authorization").replace("Bearer ", ""));
            LOGGER.info("{}", jwt.getClaims());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }
    
}
