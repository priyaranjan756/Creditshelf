package com.company.sales.creditshelf.service;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeRateService {

	public String foreignExchangeRate() throws HttpHostConnectException {
		final String uri = "https://api.exchangeratesapi.io/latest";

	 	    RestTemplate restTemplate = new RestTemplate();
		    return restTemplate.getForObject(uri, String.class);
	}
}
