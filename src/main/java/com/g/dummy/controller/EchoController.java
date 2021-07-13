package com.g.dummy.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value = "/echo")
public class EchoController {

    @GetMapping("/")
    public ResponseEntity<?> get(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody) {
        return ResponseEntity.ok().
                headers(new HttpHeaders(new LinkedMultiValueMap(headers))).
                body(requestBody);
    }

    @PostMapping("/")
    public ResponseEntity<?> post(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody){
        return ResponseEntity.ok().
                headers(new HttpHeaders(new LinkedMultiValueMap(headers))).
                body(requestBody);
    }

    @PutMapping("/")
    public ResponseEntity<?> put(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody){
        return ResponseEntity.ok().
                headers(new HttpHeaders(new LinkedMultiValueMap(headers))).
                body(requestBody);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody){
        return ResponseEntity.ok().
                headers(new HttpHeaders(new LinkedMultiValueMap(headers))).
                body(requestBody);
    }
}
