package com.company.sales.creditshelf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "olivanders_wand_shop")
public class OlivandersShopModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3293126086986999437L;

	@Id
	@Column(nullable = false,name ="id" )
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,name ="name" )
	private String name;
	
	@Column(nullable = false,name ="build_cost" )
	private String buildCost;
	
	public String getName() {
		return name;
	}

	public String getBuildCost() {
		return buildCost;
	}

	public void setBuildCost(String buildCost) {
		this.buildCost = buildCost;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(nullable = false,name ="currency" )
	private String currency;
}
