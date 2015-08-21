package com.j0ach1mmall3.permissionsshop.api;

import java.util.Date;
import java.util.List;

public class Sale {
	private String identifier;
	private String permission;
	private List<String> shops;
	private Date startDate;
	private Date endDate;
	private double percentage;
	private double amount;
	public Sale(String identifier, String permission, List<String> shops, Date startDate, Date endDate, double percentage, double amount){
		this.identifier = identifier;
		this.permission = permission;
		this.shops = shops;
		this.startDate = startDate;
		this.endDate = endDate;
		this.percentage = percentage;
		this.amount = amount;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getPermission(){
		return permission;
	}
	
	public List<String> getShops(){
		return shops;
	}
	
	public Date getStartDate(){
		return startDate;
	}
	
	public Date getEndDate(){
		return endDate;
	}
	
	public double getPercentage(){
		return percentage;
	}
	
	public double getAmount(){
		return amount;
	}
}
