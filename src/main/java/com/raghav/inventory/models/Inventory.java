package com.raghav.inventory.models;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Inventory {

	private String itemName;
	private Integer quantity;
	private String buyOrSell;//B means Buy; S means Sell
	
	private Date createdAt;
	
	public Inventory(String itemName, Integer quantity, String buyOrSell) {
		super();
		this.itemName = itemName;
		this.quantity = quantity;
		this.buyOrSell = buyOrSell;
		this.createdAt = new Date();
	}

	public Inventory() {
		
	}
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(String buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		
		return String.format(
                "Inventory[itemName=%s, quantity='%s', buyOrSell='%s']",
                itemName, quantity, buyOrSell);
	}
	
	
	
	
}
