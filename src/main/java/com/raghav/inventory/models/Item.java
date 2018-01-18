package com.raghav.inventory.models;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
@JsonIgnoreProperties(value = { "createdAt"} , allowGetters=true)
public class Item {

	private String name;
	private Double costPrice;
	private Double sellingPrice;
	private Double newSellingPrice;
	private Date createdAt;
	private Date newSellPrCreatedAt;
	private Boolean isdeleted;
	
	public Item() {
		
	}
	
	public Item(String name, Double costPrice, Double sellingPrice) {
		super();
		this.name = name;
		this.costPrice = costPrice;
		this.sellingPrice = sellingPrice;
		this.createdAt = new Date();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public Double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public Double getNewSellingPrice() {
		return newSellingPrice;
	}
	public void setNewSellingPrice(Double newSellingPrice) {
		this.newSellingPrice = newSellingPrice;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getNewSellPrCreatedAt() {
		return newSellPrCreatedAt;
	}
	public void setNewSellPrCreatedAt(Date newSellPrCreatedAt) {
		this.newSellPrCreatedAt = newSellPrCreatedAt;
	}
	
	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Item[name=%s , costPrice=%s, sellingPrice=%s ]", 
		                          name, costPrice, sellingPrice);
	}
}
