package com.codingallday.departmentservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope
@RequestMapping("/api/v1/server-config-test")
public class ServerConfigTestController {

    @Value("${spring.boot.message}")
    private String message;

    @RequestMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, String>> getMessage() {
        return new ResponseEntity<>(Map.of("message", message), HttpStatus.OK);
    }
}
