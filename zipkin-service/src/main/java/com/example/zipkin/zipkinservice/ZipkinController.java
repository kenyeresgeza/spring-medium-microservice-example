package com.example.zipkin.zipkinservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ZipkinController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value="/zipkin")
    public String zipkinService() {

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8787/currency-conversion-service/check",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});

        return response.toString();
    }

}
