package com.company.sales.creditshelf.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.dao.CompanyDao;
import com.company.sales.creditshelf.exception.DataNotFoundException;
import com.company.sales.creditshelf.exception.HostConnectException;
import com.company.sales.creditshelf.model.Sale;
import com.company.sales.creditshelf.repository.SaleRepository;
import com.company.sales.creditshelf.util.Util;

@Component
public class NetProfitService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	@Qualifier(value = "acmecorporationDao")
	private CompanyDao acmeDao;

	@Autowired
	@Qualifier(value = "capsuleCorporationDao")
	private CompanyDao capsuleDao;

	@Autowired
	@Qualifier(value = "olivandersShopDao")
	private CompanyDao olivanderDao;

	@Autowired
	private Util util;

	public JSONObject fetchNetProfit(String companyName, int year) {
		JSONObject result = new JSONObject();
		JSONObject netProfitReport = new JSONObject();
		List<Sale> queryResult = saleRepository.findByCompanyName(companyName);
		if(queryResult.isEmpty())
			throw new DataNotFoundException("Data not found into the database");
		String startDayAndMonth = "01/01";
		String endDayAndMonth = "12/31";

		String startDate = startDayAndMonth.concat("/").concat(String.valueOf(year));
		String endDate = endDayAndMonth.concat("/").concat(String.valueOf(year));
		List<Sale> sales = saleRepository.findByCompanyNameAndOrderDate(companyName, startDate, endDate);

		for(int i = 0;i<sales.size();i++) {
			try {
				util.applyExchangeRate(sales.get(i));

				JSONArray monthlyRevenue = getSaleProfitMonthly(sales,year,companyName);
				result.put("monthly-profit", monthlyRevenue);
				netProfitReport.put("response", result);
			} catch (HttpHostConnectException e) {
				throw new HostConnectException("Server is Down | connectiong to wrong IP, port",e);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return netProfitReport;
	}

	public JSONArray getSaleProfitMonthly(List<Sale> sales, int year, String companyName) throws ParseException, HttpHostConnectException {		
		List<List<String>> listOfDates = Util.getListOfDate(year);
		List<String> startDateList =  listOfDates.get(0);
		List<String> endDateList =  listOfDates.get(1);
		JSONArray jsonArray = new JSONArray();
		JSONObject monthlySalesJsonObject = new JSONObject();
		for(int i = 0;i<=11;i++) {
			String startDate =   startDateList.get(i);
			String endDate =    endDateList.get(i);
			List<Sale> queryResult = saleRepository.findByCompanyNameAndOrderDate(companyName, startDate, endDate);
			BigDecimal totalSaleMonthlyRevenue  = calculateProfit(queryResult,companyName);
			monthlySalesJsonObject.put(startDate,totalSaleMonthlyRevenue);
		}
		jsonArray.put(monthlySalesJsonObject);
		return jsonArray;
	}
	public BigDecimal calculateProfit(List<Sale> saleData, String companyName) throws HttpHostConnectException {
		BigDecimal totalProfit = new BigDecimal(0);
		for(int i = 0;i<saleData.size();i++) {
			Sale sale = saleData.get(i);
			int productId = sale.getProductId();
			BigDecimal costPrice = new BigDecimal(0);
			if(companyName.equals("Capsule Corporation"))
				costPrice = capsuleDao.fetchProductDetails(productId);
			else if(companyName.equals("Acme corporation"))
				costPrice = acmeDao.fetchProductDetails(productId);
			else if(companyName.equals("Ollivanders Wand Shop"))
				costPrice = olivanderDao.fetchProductDetails(productId);
			BigDecimal salePrice = new BigDecimal(sale.getSalesPrice());
			totalProfit = salePrice.subtract(costPrice);
		}
		return totalProfit;
	}
}
