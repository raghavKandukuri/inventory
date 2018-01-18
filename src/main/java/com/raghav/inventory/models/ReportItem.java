package com.raghav.inventory.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ReportItem {

	private String itemName;
	private Double boughtAt;
	private Double soldAt;
	private Integer availableQty;
	private Double value;
	
	public ReportItem() {
	
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getBoughtAt() {
		return boughtAt;
	}

	public void setBoughtAt(Double boughtAt) {
		this.boughtAt = boughtAt;
	}

	public Double getSoldAt() {
		return soldAt;
	}

	public void setSoldAt(Double soldAt) {
		this.soldAt = soldAt;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
