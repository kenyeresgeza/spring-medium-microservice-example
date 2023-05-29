package com.example.microservice.currency.exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private ExchangeValueRepository repository;

    @GetMapping("/check")
    public String check() {
        return "I am a currency-exchange service";
    }

    //where {from} and {to} are path variable
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        //from map to USD and to map to INR
        return repository.findByFromAndTo(from, to);
    }

}
