package com.company.sales.creditshelf.dao;

import java.math.BigDecimal;

import org.apache.http.conn.HttpHostConnectException;

public interface CompanyDao {
	BigDecimal fetchProductDetails(int id) throws HttpHostConnectException;
}
