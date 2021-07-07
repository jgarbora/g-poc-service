package com.g.dummy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller

@RequestMapping(value = "/admin")
public class AdminController {

    @GetMapping("/")
    public ResponseEntity<?> get(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> post(){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> put(){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(){
        return ResponseEntity.ok().build();
    }
}
