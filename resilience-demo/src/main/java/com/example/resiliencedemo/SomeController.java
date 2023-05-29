package com.example.resiliencedemo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SomeController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String CURRENCY_SERVICE = "currencyConversion";

    private static Logger LOGGER = LoggerFactory.getLogger(SomeController.class);

    @GetMapping("/currency")
    @CircuitBreaker(name = CURRENCY_SERVICE, fallbackMethod = "currencyFallBack")
    public String callCurrencyService() {

        String response = restTemplate.getForObject("http://api-gateway/currency-conversion-service/check", String.class);

        LOGGER.info("RESILIENCE: ******** "+response);

        return "Resilience: "+response;
    }

    public String currencyFallBack() {
        return CURRENCY_SERVICE+" is buzy...";
    }
}
