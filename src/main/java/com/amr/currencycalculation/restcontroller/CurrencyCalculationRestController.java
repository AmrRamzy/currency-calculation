package com.amr.currencycalculation.restcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.amr.currencycalculation.dto.ExchangeObject;
import com.amr.currencycalculation.dto.Limits;

@RestController
public class CurrencyCalculationRestController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	WebClient.Builder webClientBuilder;

	@GetMapping("currency-conversion/from/{from}/to/{to}/amount/{amount}")
	public ResponseEntity currencyCalculation(@PathVariable String from, @PathVariable String to,
			@PathVariable Double amount) {
		ResponseEntity response = null;
		ExchangeObject exchangeObject = null;
		String errorMessage = "System error";
		if (StringUtils.hasText(from) && StringUtils.hasText(to) && amount != null) {

			try {
//				exchangeObject = getExchangeRate(from, to, amount, exchangeObject);
//				Limits limits = restTemplate.getForObject("http://limits-service/limits-service/getLimits", Limits.class,
//						new HashMap<String, String>());
				Limits limits = webClientBuilder.build()
						.get()
						.uri("http://limits-service/limits-service/getLimits")
						.accept(MediaType.APPLICATION_JSON)
						.exchangeToMono(res -> res.bodyToMono(Limits.class))
						.block();
				if (limits != null && limits.getMin() < amount && amount < limits.getMax()) {
					exchangeObject = getExchangeRate(from, to, amount, exchangeObject);
				} else {
					errorMessage="limit Error";
					return ResponseEntity.badRequest().body(errorMessage);
				}
			} catch (Exception e) {
				System.err.println(e);
			}

		}

		if (exchangeObject == null) {
			response = ResponseEntity.badRequest().body(errorMessage);
		} else {
			response = ResponseEntity.ok(exchangeObject);
		}
		return response;
	}

	private ExchangeObject getExchangeRate(String from, String to, Double amount, ExchangeObject exchangeObject) {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("from", from);
		vars.put("to", to);
		try {
			exchangeObject = webClientBuilder.build()
					.get()
					.uri("http://CURRENCY-EXCHANGE/currency-conversion/from/{from}/to/{to}",vars)
					.accept(MediaType.APPLICATION_JSON)
					.exchangeToMono(res -> res.bodyToMono(ExchangeObject.class))
					.block();
//			exchangeObject = restTemplate.getForObject("http://CURRENCY-EXCHANGE/currency-conversion/from/{from}/to/{to}",
//					ExchangeObject.class, vars);
			exchangeObject.setAmount(amount * exchangeObject.getConversionRate());
		} catch (Exception e) {
			System.err.println(e);
		}
		return exchangeObject;
	}

}
