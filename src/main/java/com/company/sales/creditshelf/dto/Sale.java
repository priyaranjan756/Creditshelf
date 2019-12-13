package com.company.sales.creditshelf.dto;

import com.company.sales.creditshelf.constant.Currency;

public class Sale {
private String companyName;
private int orderNumber;
private String orderDate;
private int productId;
private int quantity;
private String salesPrice;
private String currency; 
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public int getOrderNumber() {
	return orderNumber;
}
public void setOrderNumber(int orderNumber) {
	this.orderNumber = orderNumber;
}
public String getOrderDate() {
	return orderDate;
}
public void setOrderDate(String orderDate) {
	this.orderDate = orderDate;
}
public int getProductId() {
	return productId;
}
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}
public String getSalesPrice() {
	return salesPrice;
}
public void setSalesPrice(String salesPrice) {
	this.salesPrice = salesPrice;
}
public void setProductId(int productId) {
	this.productId = productId;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}


}
