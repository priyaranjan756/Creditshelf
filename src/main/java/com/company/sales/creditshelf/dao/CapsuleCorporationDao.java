package com.company.sales.creditshelf.dao;

import java.math.BigDecimal;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.model.CapsuleCorporationModel;
import com.company.sales.creditshelf.repository.CapsuleCorporationRepository;
import com.company.sales.creditshelf.util.Util;

@Component(value = "capsuleCorporationDao")
public class CapsuleCorporationDao implements CompanyDao {

	@Autowired
	private CapsuleCorporationRepository capsuleCorporationRepository;

	@Autowired
	private Util util;

	@Override
	public BigDecimal fetchProductDetails(int id) throws HttpHostConnectException {
		CapsuleCorporationModel  result = capsuleCorporationRepository.findByProductId(id);
		String currency = result.getCurrency();
		String purchasePrice = result.getAssemblyCost();

		return util.exchangeCurrency(purchasePrice, currency);
	}

}
