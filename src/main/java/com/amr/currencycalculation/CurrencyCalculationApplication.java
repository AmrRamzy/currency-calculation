package com.amr.currencycalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CurrencyCalculationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyCalculationApplication.class, args);
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@LoadBalanced
	@Bean
	public WebClient.Builder getWebClient() {
		return WebClient.builder();
	}

}
