package com.company.sales.creditshelf.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.exception.DataNotFoundException;
import com.company.sales.creditshelf.exception.HostConnectException;
import com.company.sales.creditshelf.model.Sale;
import com.company.sales.creditshelf.repository.SaleRepository;

@Component
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private ExchangeRateService exchangeRateService;

	public JSONObject fetchSalesOfCompany(String companyName)  {
		List<Sale> result = saleRepository.findByCompanyName(companyName);
		JSONObject saleReport = new JSONObject();
		if(result.isEmpty())
			throw new DataNotFoundException("Data not found into the database");
			//return saleReport.put("response", "No data found");

		for(int i = 0;i<result.size();i++) {
			Sale sale = result.get(i);
			String currency = sale.getCurrency();
			if(currency.equalsIgnoreCase("EUR")) {
				continue;
			}else {
				String exchangeRates;
				try {
					exchangeRates = exchangeRateService.foreignExchangeRate();
					JSONObject jsonObject = new JSONObject(exchangeRates);
					JSONObject rates = jsonObject.getJSONObject(("rates"));
					BigDecimal value = rates.getBigDecimal((currency)).setScale(2, RoundingMode.FLOOR);
					BigDecimal originalSalePrice=new BigDecimal(sale.getSalesPrice()).setScale(2, RoundingMode.FLOOR);

					BigDecimal changedSalePrice = originalSalePrice.multiply(value).setScale(2, RoundingMode.FLOOR);
					sale.setSalesPrice(String.valueOf(changedSalePrice));
					sale.setCurrency("EUR");
				} catch (HttpHostConnectException e) {
					throw new HostConnectException("Server is Down | connectiong to wrong IP, port", e);
				}
			}

		}
		saleReport.put("response", result);
		return saleReport;
	}
}
