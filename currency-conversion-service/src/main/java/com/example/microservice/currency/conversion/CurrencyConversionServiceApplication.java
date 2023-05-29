package com.example.microservice.currency.conversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients("com.example.microservice.currency.conversion")
@EnableDiscoveryClient
@EnableEurekaClient
public class CurrencyConversionServiceApplication {

	@Bean
	@LoadBalanced
	public RestTemplate getTimeoutedRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
				new HttpComponentsClientHttpRequestFactory();
		// connection timeout in millisec
		httpComponentsClientHttpRequestFactory.setConnectTimeout(3000);

		return new RestTemplate(httpComponentsClientHttpRequestFactory);
	}

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

}
