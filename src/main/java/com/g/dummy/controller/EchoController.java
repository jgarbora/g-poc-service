package com.g.dummy.controller;

import org.apache.http.Header;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping(value = "/echo")
public class EchoController {

    @GetMapping("/")
    public ResponseEntity<?> get(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody) {
        return ResponseEntity.ok().
                headers(mapHeaders(headers)).
                body(requestBody);
    }

    @PostMapping("/")
    public ResponseEntity<?> post(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody){
        return ResponseEntity.ok().
                headers(mapHeaders(headers)).
                body(requestBody);
    }

    @PutMapping("/")
    public ResponseEntity<?> put(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody){
        return ResponseEntity.ok().
                headers(mapHeaders(headers)).
                body(requestBody);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestHeader Map<String, String> headers, @RequestBody Object requestBody){
        return ResponseEntity.ok().
                headers(mapHeaders(headers)).
                body(requestBody);
    }
    
    private HttpHeaders mapHeaders(Map<String,String> requestHeaders) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Iterator<Map.Entry<String,String>> iterator = requestHeaders.entrySet().iterator();
        while ((iterator.hasNext())) {
            Map.Entry<String,String> next = iterator.next();
            if (next.getKey().startsWith("custom"))
            responseHeaders.add(next.getKey(), next.getValue());
        }
        return responseHeaders;
    }
}
