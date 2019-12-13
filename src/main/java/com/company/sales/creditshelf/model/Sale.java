package com.company.sales.creditshelf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.company.sales.creditshelf.constant.Currency;

@Entity(name = "sale")
public class Sale implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2419775706662525864L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false,name ="company_name" )
	private String companyName;
	@Column(nullable = false, name ="order_number" )
	private int orderNumber;
	@Column(nullable = false, name ="order_date")
	private String orderDate;
	@Column(nullable = false, name ="product_id")
	private int productId;
	@Column(nullable = false, name ="quantity")
	private int quantity;
	@Column(nullable = false, name ="sales_price")
	private String salesPrice;
	@Column(nullable = false, name ="currency")
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
	public String getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
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
