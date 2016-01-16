package com.j0ach1mmall3.permissionsshop.api;

import java.util.Date;
import java.util.List;

public class Sale {
	private final String identifier;
	private final String permission;
	private final List<String> shops;
	private final Date startDate;
	private final Date endDate;
	private final double percentage;
	private final double amount;
	public Sale(String identifier, String permission, List<String> shops, Date startDate, Date endDate, double percentage, double amount) {
		this.identifier = identifier;
		this.permission = permission;
		this.shops = shops;
		this.startDate = startDate;
		this.endDate = endDate;
		this.percentage = percentage;
		this.amount = amount;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public List<String> getShops() {
		return this.shops;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public double getPercentage() {
		return this.percentage;
	}
	
	public double getAmount() {
		return this.amount;
	}
}
