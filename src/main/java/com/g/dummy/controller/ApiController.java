package com.g.dummy.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.g.dummy.dto.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ApiController {

    @GetMapping(value = "/public")
    public ResponseEntity<?> publicEndpoint(@RequestHeader Map<String, String> headers) {
        return ResponseEntity.ok("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @GetMapping(value = "/private")
    public ResponseEntity<?> privateEndpoint(@RequestHeader Map<String, String> headers) {
        printJwt(headers);
        return ResponseEntity.ok(new ResponseWrapper("All good. You can see this because you are Authenticated."));
    }

    @PostMapping(value = "/admin/endpoint-01")
    public ResponseEntity privateAdminScoped(@RequestHeader Map<String, String> headers) {
        printJwt(headers);
        return ResponseEntity.ok(new ResponseWrapper("All good. You can see this because you are Authenticated with a Token granted the 'create:user' scope"));
    }

    @PostMapping(value = "/interrogation")
    public ResponseEntity privateUserScoped(@RequestHeader Map<String, String> headers) {
        printJwt(headers);
        return ResponseEntity.ok(new ResponseWrapper("All good. You can see this because you are Authenticated with a Token granted the 'create:interrogation' scope"));
    }

    private void printJwt(Map<String, String> headers) {
        try {
            if (headers.containsKey("authorization")) {
                DecodedJWT jwt = JWT.decode(headers.get("authorization").replace("Bearer ", ""));
                log.debug("{}", jwt.getClaims());
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
    
}
