package com.company.sales.creditshelf.dao;

import java.math.BigDecimal;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.model.OlivandersShopModel;
import com.company.sales.creditshelf.repository.OlivandersShopRepository;
import com.company.sales.creditshelf.util.Util;

@Component(value = "olivandersShopDao")
public class OlivandersShopDao implements CompanyDao{

	@Autowired
	private OlivandersShopRepository olivandersShopRepository;

	@Autowired
	private Util util;

	@Override
	public BigDecimal fetchProductDetails(int id) throws HttpHostConnectException {

		OlivandersShopModel  result = olivandersShopRepository.findByProductId(id);
		String currency = result.getCurrency();
		String purchasePrice = result.getBuildCost();

		return util.exchangeCurrency(purchasePrice, currency);
	}

}
