package com.example.microservice.currency.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    @Autowired
    CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    public static final String CURRENCY_EXCHANGE_URL = "http://currency-exchange-service/currency-exchange/from/{from}/to/{to}";

    @GetMapping("/check")
    public String check() {
        return "I am a currency-converter service: "+environment.getProperty("local.server.port");
    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        Map<String, String>uriVariables=new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        //calling the currency-exchange-service
        ResponseEntity<CurrencyConversionBean>responseEntity = restTemplate.getForEntity(CURRENCY_EXCHANGE_URL,
                CurrencyConversionBean.class, uriVariables);
        CurrencyConversionBean response=responseEntity.getBody();

        return new CurrencyConversionBean(response.getId(), from,to,response.getConversionMultiple(),
                quantity, quantity.multiply(response.getConversionMultiple()),response.getPort());
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversionBean response = currencyExchangeServiceProxy.convertCurrencyFeign(from, to, quantity);

        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

}
