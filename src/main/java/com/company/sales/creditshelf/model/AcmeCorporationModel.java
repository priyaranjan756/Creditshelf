package com.company.sales.creditshelf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "acme_corporation")
public class AcmeCorporationModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8363267623398230346L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false,name ="id" )
	private int product_id;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(nullable = false,name ="description" )
	private String description;
	
	@Column(nullable = false,name ="purchase_price" )
	private String purchasePrice;
	
	@Column(nullable = false,name ="currency" )
	private String currency;
}
