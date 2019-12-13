package com.company.sales.creditshelf.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.exception.HostConnectException;
import com.company.sales.creditshelf.model.Sale;
import com.company.sales.creditshelf.repository.SaleRepository;
import com.company.sales.creditshelf.util.Util;

@Component
public class RevenueService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private ExchangeRateService exchangeRateService;

	public JSONObject fetchRevenueOfCompany(String companyName, int year){
		JSONObject result = new JSONObject();
		JSONObject revenueReport = new JSONObject();
		List<Sale> queryResult = saleRepository.findByCompanyName(companyName);
		if(queryResult.isEmpty())
			return revenueReport.put("response", "company name is not valid");
		String startDayAndMonth = "01/01";
		String endDayAndMonth = "12/31";
		String startDate = startDayAndMonth.concat("/").concat(String.valueOf(year));
		String endDate = endDayAndMonth.concat("/").concat(String.valueOf(year));
		List<Sale> sales = saleRepository.findByCompanyNameAndOrderDate(companyName, startDate, endDate);

		BigDecimal totalSalePrice = new BigDecimal(0);
		for(int i = 0;i<sales.size();i++) {
			Sale sale = sales.get(i);
			String currency = sale.getCurrency();
			BigDecimal salePrice = new BigDecimal(sale.getSalesPrice());
			if(currency.equalsIgnoreCase("EUR")) {
				totalSalePrice = totalSalePrice.add(salePrice);
			}else {
				String exchangeRates;
				try {
					exchangeRates = exchangeRateService.foreignExchangeRate();
					JSONObject jsonObject = new JSONObject(exchangeRates);
					JSONObject rates = jsonObject.getJSONObject(("rates"));
					BigDecimal value = rates.getBigDecimal((currency)).setScale(2, RoundingMode.FLOOR);
					BigDecimal originalSalePrice=new BigDecimal(sale.getSalesPrice()).setScale(2, RoundingMode.FLOOR);

					BigDecimal changedSalePrice = originalSalePrice.multiply(value).setScale(2, RoundingMode.FLOOR);
					totalSalePrice = totalSalePrice.add(changedSalePrice);
					sale.setSalesPrice(String.valueOf(changedSalePrice));
					sale.setCurrency("EUR");
				} catch (HttpHostConnectException e) {
					throw new HostConnectException("Server is Down | connectiong to wrong IP, port",e);				}

			}

		}

		try {
			JSONArray monthlyRevenue = getSaleRevenueMonthly(sales,year,companyName);

			result.put("monthly-revenue", monthlyRevenue);
			result.put("totalRevenue", totalSalePrice);
			revenueReport.put("response", result);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return revenueReport;
	}

	public JSONArray getSaleRevenueMonthly(List<Sale> sales, int year, String companyName) throws ParseException {		
		List<List<String>> listOfDates = Util.getListOfDate(year);
		List<String> startDateList =  listOfDates.get(0);
		List<String> endDateList =  listOfDates.get(1);
		JSONArray jsonArray = new JSONArray();
		JSONObject monthlySalesJsonObject = new JSONObject();
		for(int i = 0;i<=11;i++) {
			String startDate =   startDateList.get(i);
			String endDate =    endDateList.get(i);
			List<Sale> queryResult = saleRepository.findByCompanyNameAndOrderDate(companyName, startDate, endDate);
			BigDecimal totalSaleMonthlyRevenue  = sumOfSales(queryResult);
			monthlySalesJsonObject.put(startDate,totalSaleMonthlyRevenue);
		}
		jsonArray.put(monthlySalesJsonObject);
		return jsonArray;
	}

	public BigDecimal sumOfSales(List<Sale> saleData) {
		BigDecimal totalSum = new BigDecimal(0);
		for(int i = 0;i<saleData.size();i++) {
			Sale sale = saleData.get(i);
			BigDecimal salePrice = new BigDecimal(sale.getSalesPrice());
			totalSum = totalSum.add(salePrice);
		}
		return totalSum;
	}
}
