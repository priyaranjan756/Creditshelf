package com.company.sales.creditshelf.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.model.Sale;
import com.company.sales.creditshelf.service.ExchangeRateService;

@Component
public class Util {
	@Autowired
	private ExchangeRateService exchangeRateService;

	public static List<List<String>> getListOfDate(int year) throws ParseException{
		List<List<String>> listOfDates = new ArrayList<>();
		List<String> startDate = new ArrayList<>();
		startDate.add("01/01/"+year);
		startDate.add("02/01/"+year);
		startDate.add("03/01/"+year);
		startDate.add("04/01/"+year);
		startDate.add("05/01/"+year);
		startDate.add("06/01/"+year);
		startDate.add("07/01/"+year);
		startDate.add("08/01/"+year);
		startDate.add("09/01/"+year);
		startDate.add("10/01/"+year);
		startDate.add("11/01/"+year);
		startDate.add("12/01/"+year);
		List<String> endDate = new ArrayList<>();
		endDate.add("01/31/"+year);
		endDate.add("02/29/"+year);
		endDate.add("03/31/"+year);
		endDate.add("04/30/"+year);
		endDate.add("05/31/"+year);
		endDate.add("06/30/"+year);
		endDate.add("07/31/"+year);
		endDate.add("08/31/"+year);
		endDate.add("09/30/"+year);
		endDate.add("10/31/"+year);
		endDate.add("11/30/"+year);
		endDate.add("12/31/"+year);

		listOfDates.add(startDate);
		listOfDates.add(endDate);

		return listOfDates;
	}

	public  List<Sale> getSale(List<Sale> saleData ) throws HttpHostConnectException {
		BigDecimal totalSalePrice = new BigDecimal(0);
		for(int i = 0;i<saleData.size();i++) {
			Sale sale = saleData.get(i);
			String currency = sale.getCurrency();
			BigDecimal salePrice = new BigDecimal(sale.getSalesPrice());
			if(currency.equalsIgnoreCase("EUR")) {
				totalSalePrice = totalSalePrice.add(salePrice);
			}else {
				String exchangeRates = exchangeRateService.foreignExchangeRate();
				JSONObject jsonObject = new JSONObject(exchangeRates);
				JSONObject rates = jsonObject.getJSONObject(("rates"));
				BigDecimal value = rates.getBigDecimal((currency)).setScale(2, RoundingMode.FLOOR);
				BigDecimal originalSalePrice=new BigDecimal(sale.getSalesPrice()).setScale(2, RoundingMode.FLOOR);

				BigDecimal changedSalePrice = originalSalePrice.multiply(value).setScale(2, RoundingMode.FLOOR);
				totalSalePrice = totalSalePrice.add(changedSalePrice);
				sale.setSalesPrice(String.valueOf(changedSalePrice));
				sale.setCurrency("EUR");
			}

		}
		return saleData;
	}

	public void applyExchangeRate(Sale sale) throws HttpHostConnectException{
		String currency = sale.getCurrency();
		if(!currency.equalsIgnoreCase("EUR")) {
			String exchangeRates = exchangeRateService.foreignExchangeRate();
			JSONObject jsonObject = new JSONObject(exchangeRates);
			JSONObject rates = jsonObject.getJSONObject(("rates"));
			BigDecimal value = rates.getBigDecimal((currency)).setScale(2, RoundingMode.FLOOR);
			BigDecimal originalSalePrice=new BigDecimal(sale.getSalesPrice()).setScale(2, RoundingMode.FLOOR);

			BigDecimal changedSalePrice = originalSalePrice.multiply(value).setScale(2, RoundingMode.FLOOR);
			sale.setSalesPrice(String.valueOf(changedSalePrice));
			sale.setCurrency("EUR");
		}
	}

	public  BigDecimal exchangeCurrency(String price, String currency) throws HttpHostConnectException {
		BigDecimal changedSalePrice = new BigDecimal(0);
		if(!currency.equalsIgnoreCase("EUR")) {
			String exchangeRates = exchangeRateService.foreignExchangeRate();
			JSONObject jsonObject = new JSONObject(exchangeRates);
			JSONObject rates = jsonObject.getJSONObject(("rates"));
			BigDecimal value = rates.getBigDecimal((currency)).setScale(2, RoundingMode.FLOOR);
			BigDecimal originalSalePrice=new BigDecimal(price).setScale(2, RoundingMode.FLOOR);

			changedSalePrice = originalSalePrice.multiply(value).setScale(2, RoundingMode.FLOOR);

		}
		return changedSalePrice;
	}
}
