package com.company.sales.creditshelf.dao;

import java.math.BigDecimal;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.sales.creditshelf.model.AcmeCorporationModel;
import com.company.sales.creditshelf.repository.AcmeCorporationRepository;
import com.company.sales.creditshelf.util.Util;

@Component(value = "acmecorporationDao")
public class AcmeCorporationDao implements CompanyDao{

	@Autowired
	private AcmeCorporationRepository acmeCorporationRepository;

	@Autowired
	private Util util;

	@Override
	public BigDecimal fetchProductDetails(int id) throws HttpHostConnectException {

		AcmeCorporationModel  result = acmeCorporationRepository.findByProductId(id);
		String currency = result.getCurrency();
		String purchasePrice = result.getPurchasePrice();

		return util.exchangeCurrency(purchasePrice, currency);

	}

}
