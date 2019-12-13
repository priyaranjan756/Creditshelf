package com.company.sales.creditshelf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "capsule_corporation")
public class CapsuleCorporationModel  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 366575332193673113L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false,name ="id" )
	private int id;
	
	@Column(nullable = false,name = "product")
	private String product;
	
	@Column(nullable = false,name = "assembly_cost")
	private String assemblyCost;
	
	@Column(nullable = false,name = "currency")
	private String currency;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}



	public String getAssemblyCost() {
		return assemblyCost;
	}

	public void setAssemblyCost(String assemblyCost) {
		this.assemblyCost = assemblyCost;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
