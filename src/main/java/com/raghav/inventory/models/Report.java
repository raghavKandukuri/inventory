package com.raghav.inventory.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Report {

	private List<ReportItem> itemsList;
	private Double totalValue;
	private Double profit;
	
	public Report() {
	}
	
	public List<ReportItem> getItemsList() {
		return itemsList;
	}



	public void setItemsList(List<ReportItem> itemsList) {
		this.itemsList = itemsList;
	}



	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}
	
	
}
